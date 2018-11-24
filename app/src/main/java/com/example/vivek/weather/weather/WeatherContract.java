package com.example.vivek.weather.weather;

import com.example.vivek.weather.application.BasePresenter;
import com.example.vivek.weather.application.BaseView;
import com.example.vivek.weather.weather.models.DayForeCastModel;

import java.util.List;


public interface WeatherContract {

    interface View extends BaseView<Presenter> {

        void onWeatherForeCastFetched(List<DayForeCastModel> dayForeCastModels);
    }

    interface Presenter extends BasePresenter<View> {

    }
}
