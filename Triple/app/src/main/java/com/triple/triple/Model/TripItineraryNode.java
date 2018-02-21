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
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("image")
    @Expose
    private String image;
    @SerializedName("type")
    @Expose
    private Object type;
    @SerializedName("tag")
    @Expose
    private String tag;
    @SerializedName("time")
    @Expose
    private String time;
    @SerializedName("duration")
    @Expose
    private int duration;
    @SerializedName("distance")
    @Expose
    private int distance;
    @SerializedName("travel_duration")
    @Expose
    private int travel_duration;
    @SerializedName("fare")
    @Expose
    private Object fare;
    @SerializedName("mode")
    @Expose
    private Object mode;
    @SerializedName("route")
    @Expose
    private Object route;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Object getType() {
        return type;
    }

    public void setType(Object type) {
        this.type = type;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

    public int getTravel_duration() {
        return travel_duration;
    }

    public void setTravel_duration(int travel_duration) {
        this.travel_duration = travel_duration;
    }

    public Object getFare() {
        return fare;
    }

    public void setFare(Object fare) {
        this.fare = fare;
    }

    public Object getMode() {
        return mode;
    }

    public void setMode(Object mode) {
        this.mode = mode;
    }

    public Object getRoute() {
        return route;
    }

    public void setRoute(Object route) {
        this.route = route;
    }
}
