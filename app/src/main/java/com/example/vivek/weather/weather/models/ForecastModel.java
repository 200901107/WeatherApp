package com.example.vivek.weather.weather.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ForecastModel {

    @SerializedName("forecast")
    private ForecastDayModel foreCastModel;

    @SerializedName("location")
    private LocationModel locationModel;

    public ForecastModel() {

    }

    public void setForeCastModel(ForecastDayModel foreCastModel) {
        this.foreCastModel = foreCastModel;
    }

    public ForecastDayModel getForeCastModel() {
        return this.foreCastModel;
    }

    public void setLocationModel(LocationModel locationModel) {
        this.locationModel = locationModel;
    }

    public LocationModel getLocationModel() {
        return locationModel;
    }
}
