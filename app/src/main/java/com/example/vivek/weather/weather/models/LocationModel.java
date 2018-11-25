package com.example.vivek.weather.weather.models;

import com.google.gson.annotations.SerializedName;

public class LocationModel {

    @SerializedName("name")
    private String location;

    public LocationModel() {

    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getLocation() {
        return location;
    }
}
