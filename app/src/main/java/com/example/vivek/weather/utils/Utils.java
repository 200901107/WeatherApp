package com.example.vivek.weather.utils;

import android.content.Context;
import android.os.Build;

import java.util.Locale;

public class Utils {

    public static Locale getCurrentLocale(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            return context.getResources().getConfiguration().getLocales().get(0);
        } else {
            //no inspection deprecation
            return context.getResources().getConfiguration().locale;
        }
    }

}
