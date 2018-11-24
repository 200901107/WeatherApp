package com.example.vivek.weather.network;

import com.example.vivek.weather.weather.models.ForecastModel;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiService {

    @GET("forecast.json/")
    Observable<ForecastModel> fetchForeCast(@Query("key") String apiKey, @Query("q") String zipCode, @Query("days") String days);
}
