package com.example.vivek.weather.weather.models;

import com.google.gson.annotations.SerializedName;

public class DayForeCastModel {

    @SerializedName("date")
    private String date;

    @SerializedName("day")
    private ForecastDetails forecastDetails;

    public DayForeCastModel() {

    }

    public void setForecastDetails(ForecastDetails forecastDetails) {
        this.forecastDetails = forecastDetails;
    }

    public ForecastDetails getForeCastDetails() {
        return forecastDetails;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDate() {
        return date;
    }
}
