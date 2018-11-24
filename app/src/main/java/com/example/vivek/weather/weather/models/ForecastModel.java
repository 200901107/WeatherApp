package com.example.vivek.weather.weather.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ForecastModel {

    @SerializedName("forecast")
    private ForecastDayModel foreCastModel;

    public ForecastModel() {

    }

    public void setForeCastModel(ForecastDayModel foreCastModel) {
        this.foreCastModel = foreCastModel;
    }

    public ForecastDayModel getForeCastModel() {
        return this.foreCastModel;
    }
}
