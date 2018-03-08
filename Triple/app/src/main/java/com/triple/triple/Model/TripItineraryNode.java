package com.triple.triple.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Kevin on 2018/2/21.
 */

public class TripItineraryNode implements Serializable {

    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("attraction_id")
    @Expose
    private int attraction_id;
    @SerializedName("visit_time")
    @Expose
    private String visit_time;
    @SerializedName("duration")
    @Expose
    private int duration;
    @SerializedName("travel_duration")
    @Expose
    private int travel_duration;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("distance")
    @Expose
    private int distance;
    @SerializedName("fare")
    @Expose
    private Object fare;
    @SerializedName("peak_hour")
    @Expose
    private Boolean peak_hour;
    @SerializedName("mode")
    @Expose
    private String mode;
    @SerializedName("attraction")
    @Expose
    private Attraction attraction;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAttraction_id() {
        return attraction_id;
    }

    public void setAttraction_id(int attraction_id) {
        this.attraction_id = attraction_id;
    }

    public String getVisit_time() {
        return visit_time;
    }

    public void setVisit_time(String visit_time) {
        this.visit_time = visit_time;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public int getTravel_duration() {
        return travel_duration;
    }

    public void setTravel_duration(int travel_duration) {
        this.travel_duration = travel_duration;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

    public Object getFare() {
        return fare;
    }

    public void setFare(Object fare) {
        this.fare = fare;
    }

    public Boolean getPeak_hour() {
        return peak_hour;
    }

    public void setPeak_hour(Boolean peak_hour) {
        this.peak_hour = peak_hour;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public Attraction getAttraction() {
        return attraction;
    }

    public void setAttraction(Attraction attraction) {
        this.attraction = attraction;
    }

    @Override
    public String toString() {
        return "TripItineraryNode{" +
                "id=" + id +
                ", attraction_id=" + attraction_id +
                ", visit_time='" + visit_time + '\'' +
                ", duration=" + duration +
                ", travel_duration=" + travel_duration +
                ", type='" + type + '\'' +
                ", distance=" + distance +
                ", fare=" + fare +
                ", peak_hour=" + peak_hour +
                ", mode='" + mode + '\'' +
                ", attraction=" + attraction +
                '}';
    }
}
