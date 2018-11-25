package com.example.vivek.weather.weather;

import com.example.vivek.weather.application.BasePresenter;
import com.example.vivek.weather.application.BaseView;
import com.example.vivek.weather.weather.models.ForecastModel;


public interface WeatherContract {

    interface View extends BaseView<Presenter> {

        void onWeatherForeCastFetched(ForecastModel dayForeCastModels);

        void onWeatherFetchFailed();
    }

    interface Presenter extends BasePresenter<View> {

    }
}
