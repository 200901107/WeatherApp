package com.example.vivek.weather.application;

import java.io.IOException;
import java.util.List;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class AuthInterceptor implements Interceptor{

    private static String API_KEY = "da6c566db4164b8c937110410182411";

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        return chain.proceed(request);
    }

    public static String getApiKey() {
        return API_KEY;
    }
}
