package com.example.vivek.weather.application;

import android.app.Application;
import android.os.StrictMode;

import com.example.vivek.weather.BuildConfig;
import com.example.vivek.weather.application.di.ApplicationComponent;
import com.example.vivek.weather.application.di.DaggerApplicationComponent;
import com.example.vivek.weather.application.di.modules.ContextModule;
import com.example.vivek.weather.network.ApiService;

public class AppApplication extends Application{

    private static AppApplication mAppController;
    private ApplicationComponent mComponent;

    public static AppApplication getInstance() {
        if (mAppController == null) {
            throw new NullPointerException("Application context is null");
        }
        return mAppController;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mComponent = DaggerApplicationComponent.builder().contextModule(new ContextModule(this)).build();
        mAppController = this;
        if (BuildConfig.DEBUG) {
            StrictMode.enableDefaults();
        }
    }

    public ApplicationComponent getComponent() {
        return mComponent;
    }

    public ApiService getApiService() {
        return getComponent().getApiService();
    }
}
