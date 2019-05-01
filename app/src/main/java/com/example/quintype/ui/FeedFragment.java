package com.example.quintype.ui;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.quintype.R;
import com.example.quintype.data.entity.Collection;
import com.example.quintype.data.entity.Item;
import com.example.quintype.datamodel.Status;
import com.example.quintype.viewmodel.CollectionViewModel;
import com.example.quintype.viewmodel.FeedViewModel;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FeedFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FeedFragment extends Fragment implements ItemAdater.ItemAdaterListner {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;


    @BindView(R.id.recyclerview)
    RecyclerView recyclerView;


    ItemAdater itemAdater;

    private Unbinder uibinder;
    private FeedViewModel feedviewModel;
    private Observer<List<Item>> feedObserver;
    private Observer<Status> networkFetchStatusObserver;

    public FeedFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FeedFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static FeedFragment newInstance(String param1, String param2) {
        FeedFragment fragment = new FeedFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        itemAdater = new ItemAdater(getContext(), this);
        feedviewModel = ViewModelProviders.of(this).get(FeedViewModel.class);
        feedObserver = new Observer<List<Item>>() {
            @Override
            public void onChanged(List<Item> items) {
                itemAdater.onDataChange(items);
            }
        };

        networkFetchStatusObserver =  new Observer<Status>() {
            @Override
            public void onChanged(Status status) {
                if(status == Status.FAILURE){
                    Toast.makeText(getContext(), "Unable to fetch feed from server", Toast.LENGTH_SHORT).show();
                }
            }
        };
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_feed, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        uibinder = ButterKnife.bind(this, view);
        recyclerView.setAdapter(itemAdater);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement com.example.quintype.ui.OnFragmentInteractionListener");
        }
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        feedviewModel.getFeedFromCache().observe(this, feedObserver);
        feedviewModel.getFeedStatusLiveData().observe(this, networkFetchStatusObserver);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        uibinder.unbind();
        feedviewModel.getFeedFromCache().removeObserver(feedObserver);
        feedviewModel.getFeedStatusLiveData().removeObserver(networkFetchStatusObserver);
    }


    @Override
    public void observe(Observer observer, Item item) {
        feedviewModel.getCollectionLiveData(item).observe(this, observer);
        
    }

    @Override
    public void getCollectionFromNetwork(Item item) {
        feedviewModel.getCollectionFromNetwork(item);
    }

    @Override
    public void removeObserver(Observer<Collection> observer, Item item) {
        feedviewModel.getCollectionLiveData(item).removeObserver(observer);

    }


}
