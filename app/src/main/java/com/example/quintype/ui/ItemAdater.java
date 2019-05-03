package com.example.quintype.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.quintype.R;
import com.example.quintype.data.entity.Collection;
import com.example.quintype.data.entity.Item;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ItemAdater extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    public static final int ITEMS = 0;
    public static final int COLLECTION = 1;
    public static final int STORIES = 2;
    private int viewType = -1;
    private List<Item> mData;
    private ItemAdaterListner itemAdaterListner;
    private Context context;

    public ItemAdater(Context context, ItemAdaterListner itemAdaterListner) {
        this.mData = new ArrayList<>();
        this.itemAdaterListner = itemAdaterListner;
        this.context = context;
    }
    public ItemAdater(Context context, ItemAdaterListner itemAdaterListner, int viewType) {
        this.mData = new ArrayList<>();
        this.itemAdaterListner = itemAdaterListner;
        this.context = context;
        this.viewType = viewType;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        switch (viewType){
            case ITEMS:
                ItemViewHolder itemViewHolder = new ItemViewHolder(LayoutInflater.from(context).inflate(R.layout.item, parent, false));
                return itemViewHolder;
            case COLLECTION:
                CollectionViewHolder collectionViewHolder = new CollectionViewHolder(LayoutInflater.from(context).inflate(R.layout.collection, parent, false));
                return collectionViewHolder;
            case STORIES:
                StoryViewHolder storyViewHolder = new StoryViewHolder(LayoutInflater.from(context).inflate(R.layout.story, parent, false));
                return storyViewHolder;
            default:
                return null;
        }

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        switch (holder.getItemViewType()){
            case ITEMS:
                ((ItemViewHolder)holder).bindView(mData.get(position));
                break;
            case COLLECTION:
                ((CollectionViewHolder)holder).bindView(mData.get(position));
                break;
            case STORIES:
                ((StoryViewHolder)holder).bindView(mData.get(position));
                break;


        }



    }


    @Override
    public int getItemViewType(int position) {

        if(viewType!= -1){
            return viewType;
        }

        String type = mData.get(position).getType();
        switch (type){
            case "story":
                return ITEMS;
            case "collection":
                return COLLECTION;
            default:
                return ITEMS;

        }
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public void onDataChange(List<Item> data) {
        this.mData.clear();
        this.mData.addAll(data);
        notifyDataSetChanged();

    }


    class StoryViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.name)
        TextView name;

        @BindView(R.id.imageView)
        ImageView imageView;

        public StoryViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    itemAdaterListner.onStorySelected(mData.get(getAdapterPosition()));
                }
            });
        }


        public void bindView(Item item) {
            name.setText(item.getStory().getHeadline());
            Glide.with(context)
                    .load(item.getStory().getHeroImage())
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(imageView);
        }
    }


    class ItemViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.name)
        TextView name;

        @BindView(R.id.imageView)
        ImageView imageView;

        public ItemViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    itemAdaterListner.onStorySelected(mData.get(getAdapterPosition()));
                }
            });
        }

        public void bindView(Item item) {
            name.setText(item.getStory().getHeadline());
            Glide.with(context)
                    .load(item.getStory().getHeroImage())
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(imageView);
        }
    }

    class CollectionViewHolder extends RecyclerView.ViewHolder {

        private final Observer<Collection> observer;
        @BindView(R.id.recyclerview)
        RecyclerView recyclerView;


        @BindView(R.id.textView2)
        TextView heading;

        ItemAdater adapter;

        private Item item;

        public CollectionViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            adapter = new ItemAdater(((Fragment)itemAdaterListner).getContext(), itemAdaterListner, STORIES);
            recyclerView.setAdapter(adapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(((Fragment)itemAdaterListner).getContext(), LinearLayoutManager.HORIZONTAL, false));
            observer = new Observer<Collection>() {
                @Override
                public void onChanged(Collection collection) {
                    if(collection !=null){
                        adapter.onDataChange(collection.getItems());
                        heading.setText(collection.getName());
                    }
                    else{
                        itemAdaterListner.getCollectionFromNetwork(item);
                    }
                }
            };
        }



        public void bindView(Item item) {
            this.item = item;
        }

        
        

    }

    @Override
    public void onViewAttachedToWindow(@NonNull RecyclerView.ViewHolder holder) {
        super.onViewAttachedToWindow(holder);
        if(holder instanceof CollectionViewHolder){
            itemAdaterListner.observe(((CollectionViewHolder)holder).observer, mData.get(holder.getAdapterPosition()));
        }
    }

    @Override
    public void onViewDetachedFromWindow(@NonNull RecyclerView.ViewHolder holder) {
        super.onViewDetachedFromWindow(holder);
        if(holder instanceof CollectionViewHolder){
            itemAdaterListner.removeObserver(((CollectionViewHolder)holder).observer,((CollectionViewHolder) holder).item);
            
        }
    }

    public interface ItemAdaterListner{

        void observe(Observer observer, Item item);

        void getCollectionFromNetwork(Item item);

        void removeObserver(Observer<Collection> observer, Item item);

        void onStorySelected(Item item);
    }
}
