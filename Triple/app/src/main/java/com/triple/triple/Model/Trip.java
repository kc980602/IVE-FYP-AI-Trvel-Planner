package com.triple.triple.Model;



import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Arrays;

/**
 * Created by Kevin on 2018/1/23.
 */

public class Trip implements Serializable {

    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("title")
    @Expose
    private String name;
    @SerializedName("visit_date")
    @Expose
    private String visit_date;
    @SerializedName("visit_length")
    @Expose
    private int visit_length;
    @SerializedName("owner")
    @Expose
    private String owner;
    @SerializedName("owner_id")
    @Expose
    private int owner_id;
    @SerializedName("image")
    @Expose
    private String image;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVisit_date() {
        return visit_date;
    }

    public void setVisit_date(String visit_date) {
        this.visit_date = visit_date;
    }

    public int getVisit_length() {
        return visit_length;
    }

    public void setVisit_length(int visit_length) {
        this.visit_length = visit_length;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getOwner_id() {
        return owner_id;
    }

    public void setOwner_id(int owner_id) {
        this.owner_id = owner_id;
    }

    @Override
    public String toString() {
        return "Trip{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", visit_date='" + visit_date + '\'' +
                ", visit_length=" + visit_length +
                ", owner='" + owner + '\'' +
                ", owner_id=" + owner_id +
                ", image='" + image + '\'' +
                '}';
    }
}
