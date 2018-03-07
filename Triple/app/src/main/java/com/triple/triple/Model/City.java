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
    @SerializedName("country")
    @Expose
    private String country;
    @SerializedName("Taipei")
    @Expose
    private String Taipei;
    @SerializedName("photo")
    @Expose
    private String photo;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getTaipei() {
        return Taipei;
    }

    public void setTaipei(String taipei) {
        Taipei = taipei;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }
}
