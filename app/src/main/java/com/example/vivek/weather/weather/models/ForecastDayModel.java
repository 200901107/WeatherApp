package com.example.vivek.weather.weather.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ForecastDayModel {

    @SerializedName("forecastday")
    private List<DayForeCastModel> dayForeCastModels;

    public ForecastDayModel() {

    }

    public void setDayForeCastModels(List<DayForeCastModel> list) {
        this.dayForeCastModels = list;
    }

    public List<DayForeCastModel> getDayForeCastModels() {
        return dayForeCastModels;
    }
}
