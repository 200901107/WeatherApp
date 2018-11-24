package com.example.vivek.weather.weather;

import com.example.vivek.weather.application.components.ResourceProvider;
import com.example.vivek.weather.application.di.qualifier.ActivityScoped;
import com.example.vivek.weather.network.ApiService;

import dagger.Module;
import dagger.Provides;

@Module
public class WeatherModule {

    private WeatherActivity weatherActivity;

    public WeatherModule(WeatherActivity weatherActivity) {
        this.weatherActivity = weatherActivity;
    }

    @ActivityScoped
    @Provides
    public WeatherInteractor provideInteractor(ResourceProvider resourceProvider, ApiService apiService) {
        return new WeatherInteractor(apiService, resourceProvider);
    }
}
