package com.triple.triple.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Kevin on 2018/3/8.
 */

public class City implements Serializable {

    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("country")
    @Expose
    private String country;
    @SerializedName("photo")
    @Expose
    private String photo;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("timezone")
    @Expose
    private String timezone;
    @SerializedName("latitude")
    @Expose
    private Double latitude ;
    @SerializedName("longitude")
    @Expose
    private Double longitude;

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

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTimezone() {return timezone;}

    public void setTimezone(String timezone) { this.timezone = timezone;}

    public Double getLatitude() {return latitude;}

    public void setLatitude(Double latitude) { this.latitude = latitude;}

    public Double getLongitude() { return longitude;}

    public void setLongitude(Double longitude) { this.longitude = longitude;}
}
