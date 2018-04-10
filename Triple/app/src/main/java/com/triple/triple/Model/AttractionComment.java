package com.triple.triple.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by HaYYY on 2018/4/8.
 */

public class AttractionComment implements Serializable{

    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("rating")
    @Expose
    private int rating;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("content")
    @Expose
    private String content;
    @SerializedName("photos")
    @Expose
    private List<String> photos;
    @SerializedName("upvote")
    @Expose
    private int upvote;
    @SerializedName("created_at")
    @Expose
    private int created_at;
    @SerializedName("updated_at")
    @Expose
    private int updated_at;
    @SerializedName("user")
    @Expose
    private ReviewUser user;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public List<String> getPhotos() {
        return photos;
    }

    public void setPhotos(List<String> photos) {
        this.photos = photos;
    }

    public int getUpvote() {
        return upvote;
    }

    public void setUpvote(int upvote) {
        this.upvote = upvote;
    }

    public int getCreated_at() {
        return created_at;
    }

    public void setCreated_at(int created_at) {
        this.created_at = created_at;
    }

    public int getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(int updated_at) {
        this.updated_at = updated_at;
    }

    public ReviewUser getUser() {
        return user;
    }

    public void setUser(ReviewUser user) {
        this.user = user;
    }
}
