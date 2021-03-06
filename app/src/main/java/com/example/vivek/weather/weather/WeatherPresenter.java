package com.example.vivek.weather.weather;

import android.support.annotation.Nullable;

import com.example.vivek.weather.application.components.ResourceProvider;
import com.example.vivek.weather.application.schedulers.SchedulerProvider;
import com.example.vivek.weather.weather.models.ForecastModel;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;

public class WeatherPresenter implements WeatherContract.Presenter{

    @Nullable
    private WeatherContract.View mView;
    private WeatherInteractor weatherInteractor;
    private ResourceProvider mResourceProvider;
    private CompositeDisposable mDisposables = new CompositeDisposable();
    private SchedulerProvider mSchedulerProvider;

    @Inject
    public WeatherPresenter(WeatherInteractor weatherInteractor, ResourceProvider resourceProvider, SchedulerProvider schedulerProvider) {
        this.weatherInteractor = weatherInteractor;
        this.mResourceProvider = resourceProvider;
        this.mSchedulerProvider = schedulerProvider;
    }

    @Override
    public void subscribeWithView(WeatherContract.View view) {
        mView = view;
    }

    @Override
    public void unsubscribeWithView() {
        mDisposables.clear();
        this.mView = null;
    }

    public void fetchWeatherForeCasts(String parameter, String futureDays) {
        if (parameter != null && futureDays != null) {
            mDisposables.add(weatherInteractor.fetchWeatherForeCasts(parameter, futureDays)
                    .subscribeOn(mSchedulerProvider.io()).observeOn(mSchedulerProvider.ui())
                    .subscribe(this::onForeCastFetched, throwable -> {
                        onForesCastFetchFailed();
                        throwable.printStackTrace();
                    }));
        } else {
            onForesCastFetchFailed();
        }
    }

    private void onForesCastFetchFailed() {
        if (mView != null)
            mView.onWeatherFetchFailed();
    }

    private void onForeCastFetched(ForecastModel forecastModel) {
        if (mView != null) {
            mView.onWeatherForeCastFetched(forecastModel);
        }
    }
}
