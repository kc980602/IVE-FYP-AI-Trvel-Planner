package com.triple.triple.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Kevin on 2018/3/8.
 */

public class SystemProperty implements Serializable{

    @SerializedName("income")
    @Expose
    private List<KeyValue> income;
    @SerializedName("age")
    @Expose
    private List<KeyValue> age;
    @SerializedName("city")
    @Expose
    private List<City> city;

    public List<KeyValue> getIncome() {
        return income;
    }

    public void setIncome(List<KeyValue> income) {
        this.income = income;
    }

    public List<KeyValue> getAge() {
        return age;
    }

    public void setAge(List<KeyValue> age) {
        this.age = age;
    }

    public List<City> getCity() {
        return city;
    }

    public void setCity(List<City> city) {
        this.city = city;
    }

    @Override
    public String toString() {
        return "SystemProperty{" +
                "income=" + income +
                ", age=" + age +
                ", city=" + city +
                '}';
    }
}
