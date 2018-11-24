package com.example.vivek.weather.application.di.modules;

import android.content.Context;

import com.example.vivek.weather.application.components.ResourceProvider;
import com.example.vivek.weather.application.di.qualifier.ApplicationContext;
import com.example.vivek.weather.application.di.scope.ApplicationScoped;
import com.example.vivek.weather.application.schedulers.SchedulerProvider;
import com.example.vivek.weather.network.di.module.ApiServiceModule;

import dagger.Module;
import dagger.Provides;

@Module(includes = {ContextModule.class, ApiServiceModule.class})
public class ApplicationModule {

    @Provides
    @ApplicationScoped
    public ResourceProvider resourceProvider(@ApplicationContext Context context) {
        return new ResourceProvider(context);
    }

    @Provides
    @ApplicationScoped
    public SchedulerProvider schedulerProvider() {
        return SchedulerProvider.getInstance();
    }
}
