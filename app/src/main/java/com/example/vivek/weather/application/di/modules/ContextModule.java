package com.example.vivek.weather.application.di.modules;

import android.content.Context;

import com.example.vivek.weather.application.di.qualifier.ApplicationContext;
import com.example.vivek.weather.application.di.scope.ApplicationScoped;

import dagger.Module;
import dagger.Provides;

@Module
public class ContextModule {

    private final Context context;

    public ContextModule(Context context) {
        this.context = context;
    }

    @Provides
    @ApplicationContext
    @ApplicationScoped
    public Context context() {
        return this.context;
    }

}
