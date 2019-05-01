
package com.example.quintype.datamodel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Story {

    @SerializedName("author-name")
    @Expose
    private String authorName;
    @SerializedName("headline")
    @Expose
    private String headline;
    @SerializedName("slug")
    @Expose
    private String slug;
    @SerializedName("summary")
    @Expose
    private String summary;
    @SerializedName("hero-image")
    @Expose
    private String heroImage;

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public String getHeadline() {
        return headline;
    }

    public void setHeadline(String headline) {
        this.headline = headline;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getHeroImage() {
        return heroImage;
    }

    public void setHeroImage(String heroImage) {
        this.heroImage = heroImage;
    }

}
