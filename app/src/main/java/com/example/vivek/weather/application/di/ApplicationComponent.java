package com.example.vivek.weather.application.di;

import android.content.Context;

import com.example.vivek.weather.application.components.ResourceProvider;
import com.example.vivek.weather.application.di.modules.ApplicationModule;
import com.example.vivek.weather.application.di.qualifier.ApplicationContext;
import com.example.vivek.weather.application.di.scope.ApplicationScoped;
import com.example.vivek.weather.network.ApiService;

import dagger.Component;

@ApplicationScoped
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {

    @ApplicationContext
    Context getContext();

    ResourceProvider getResourceProvider();

    ApiService getApiService();

}
