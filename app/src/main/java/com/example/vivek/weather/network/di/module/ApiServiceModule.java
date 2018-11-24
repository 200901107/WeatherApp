package com.example.vivek.weather.network.di.module;

import com.example.vivek.weather.BuildConfig;
import com.example.vivek.weather.application.di.modules.NetworkModule;
import com.example.vivek.weather.application.di.scope.ApplicationScoped;
import com.example.vivek.weather.network.ApiService;
import com.example.vivek.weather.network.di.qualifier.ApiServiceQualifier;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module(includes = NetworkModule.class)
public class ApiServiceModule {

    public static String getBaseUrl() {
        return BuildConfig.WeatherUrl;
    }

    @Provides
    @ApplicationScoped
    public ApiService crsApiService(@ApiServiceQualifier Retrofit retrofit) {
        return retrofit.create(ApiService.class);
    }

    @Provides
    @ApplicationScoped
    @ApiServiceQualifier
    public Retrofit provideRetrofit(OkHttpClient httpClient) {
        return new Retrofit.Builder()
                .baseUrl(getBaseUrl())
                .client(httpClient)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create()).build();
    }
}

