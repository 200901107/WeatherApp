package com.example.vivek.weather.weather;

import com.example.vivek.weather.application.di.ApplicationComponent;
import com.example.vivek.weather.application.di.qualifier.ActivityScoped;

import dagger.Component;

@Component(dependencies = {ApplicationComponent.class}, modules = {WeatherModule.class})
@ActivityScoped
public interface WeatherComponent {
    void inject(WeatherActivity weatherActivity);
}