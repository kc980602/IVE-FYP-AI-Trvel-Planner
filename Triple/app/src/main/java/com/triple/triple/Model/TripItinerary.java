package com.triple.triple.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

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
    @SerializedName("nodes")
    @Expose
    private List<TripItineraryNode> nodes;

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

    public List<TripItineraryNode> getNodes() {
        return nodes;
    }

    public void setNodes(List<TripItineraryNode> nodes) {
        this.nodes = nodes;
    }
}
