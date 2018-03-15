package com.triple.triple.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by KC on 3/15/2018.
 */

public class DataMeta implements Serializable{
    @SerializedName("data")
    @Expose
    private List<Attraction> attractions;
    @SerializedName("meta")
    @Expose
    private Pagination pagination;

    public List<Attraction> getAttractions() {
        return attractions;
    }

    public void setAttractions(List<Attraction> attractions) {
        this.attractions = attractions;
    }

    public Pagination getPagination() {
        return pagination;
    }

    public void setPagination(Pagination pagination) {
        this.pagination = pagination;
    }
}
