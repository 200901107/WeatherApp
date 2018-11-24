package com.example.vivek.weather.weather.models;

import com.google.gson.annotations.SerializedName;

public class ForecastDetails {

    @SerializedName("avgtemp_c")
    private double avgTemperature;

    public ForecastDetails() {

    }

    public void setAvgTemperature(double avgTemperature) {
        this.avgTemperature = avgTemperature;
    }

    public double getAvgTemperature() {
        return avgTemperature;
    }
}
