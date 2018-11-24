package com.example.vivek.weather.weather;

import com.example.vivek.weather.application.AuthInterceptor;
import com.example.vivek.weather.application.components.ResourceProvider;
import com.example.vivek.weather.network.ApiService;
import com.example.vivek.weather.weather.models.ForecastModel;

import io.reactivex.Observable;

public class WeatherInteractor {

    private ApiService apiService;
    private ResourceProvider resourceProvider;

    WeatherInteractor(ApiService apiService, ResourceProvider resourceProvider) {
        this.apiService = apiService;
        this.resourceProvider = resourceProvider;
    }

    public Observable<ForecastModel> fetchWeatherForeCasts(String zipCode, String futureDays) {
        return apiService.fetchForeCast(AuthInterceptor.getApiKey(), zipCode, futureDays);
    }
}
