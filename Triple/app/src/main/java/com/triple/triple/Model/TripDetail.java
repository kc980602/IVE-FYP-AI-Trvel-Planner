package com.triple.triple.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Kevin on 2018/2/17.
 */

public class TripDetail {

    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("owner_id")
    @Expose
    private String owner_id;
    @SerializedName("visit_date")
    @Expose
    private String visit_date;
    @SerializedName("visit_length")
    @Expose
    private String visit_length;
    @SerializedName("collaborators")
    @Expose
    private Object collaborators;
    @SerializedName("itinerary")
    @Expose
    private Object itinerary;
    private List<TripItinerary> itineraryList;
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getOwner_id() {
        return owner_id;
    }

    public void setOwner_id(String owner_id) {
        this.owner_id = owner_id;
    }

    public String getVisit_date() {
        return visit_date;
    }

    public void setVisit_date(String visit_date) {
        this.visit_date = visit_date;
    }

    public String getVisit_length() {
        return visit_length;
    }

    public void setVisit_length(String visit_length) {
        this.visit_length = visit_length;
    }

    public Object getCollaborators() {
        return collaborators;
    }

    public void setCollaborators(Object collaborators) {
        this.collaborators = collaborators;
    }

    public Object getItinerary() {
        return itinerary;
    }

    public void setItinerary(Object itinerary) {
        this.itinerary = itinerary;
    }

    public List<TripItinerary> getItineraryList() {
        return itineraryList;
    }

    public void setItineraryList(List<TripItinerary> itineraryList) {
        this.itineraryList = itineraryList;
    }

    @Override
    public String toString() {
        return "TripDetail{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", owner_id='" + owner_id + '\'' +
                ", visit_date='" + visit_date + '\'' +
                ", visit_length='" + visit_length + '\'' +
                ", collaborators=" + collaborators +
                ", itineraryList=" + itineraryList +
                '}';
    }
}
