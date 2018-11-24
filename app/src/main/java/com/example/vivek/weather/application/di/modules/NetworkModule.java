package com.example.vivek.weather.application.di.modules;

import android.content.Context;

import com.example.vivek.weather.BuildConfig;
import com.example.vivek.weather.application.di.qualifier.ApplicationContext;
import com.example.vivek.weather.application.di.scope.ApplicationScoped;

import java.io.File;
import java.util.concurrent.TimeUnit;

import dagger.Module;
import dagger.Provides;
import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;

@Module(includes = ContextModule.class)
public class NetworkModule {

    @Provides
    @ApplicationScoped
    public OkHttpClient okHttpClient(HttpLoggingInterceptor logging, Cache cache) {

        OkHttpClient.Builder httpClientBuilder = new OkHttpClient.Builder();

        // TODO - Remove this until SSL issues are identified.
        httpClientBuilder.hostnameVerifier((hostName, sslSession) -> true);

        httpClientBuilder.connectTimeout(2, TimeUnit.MINUTES);
        httpClientBuilder.writeTimeout(2, TimeUnit.MINUTES);
        httpClientBuilder.readTimeout(2, TimeUnit.MINUTES);
        if (BuildConfig.DEBUG) {
            if (!httpClientBuilder.interceptors().contains(logging)) {
                httpClientBuilder.addInterceptor(logging);
            }
        }

        httpClientBuilder.cache(cache);

        return httpClientBuilder.build();
    }

    @Provides
    @ApplicationScoped
    public Cache cache(File cacheFile) {
        // 10 MB Cache
        return new Cache(cacheFile, 10 * 1000 * 1000);
    }

    @Provides
    @ApplicationScoped
    public File cacheFile(@ApplicationContext Context context) {
        return new File(context.getCacheDir(), "okhttp3.cache");
    }

    @Provides
    @ApplicationScoped
    HttpLoggingInterceptor loggingInterceptor() {
        return new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY);
    }
}
