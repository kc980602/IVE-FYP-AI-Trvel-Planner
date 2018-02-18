package com.triple.triple.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Kevin on 2018/1/25.
 */

public class TripItinerary {

    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("visit_date")
    @Expose
    private String visit_date;
    //    @SerializedName("nodes")
//    @Expose
//    private String nodes;
    private String name;
    private String duration;
    private String tags;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getVisit_date() {
        return visit_date;
    }

    public void setVisit_date(String visit_date) {
        this.visit_date = visit_date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    @Override
    public String toString() {
        return "TripItinerary{" +
                "id=" + id +
                ", visit_date='" + visit_date + '\'' +
                '}';
    }
}
