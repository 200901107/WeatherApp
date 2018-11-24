package com.example.vivek.weather.application.components;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;

import com.example.vivek.weather.utils.Utils;

import java.util.Locale;

public class ResourceProvider {

    private Context context;

    public ResourceProvider(Context context) {
        this.context = context;
    }

    public Context getContext() {
        return context;
    }

    public String getString(int resId) {
        return context.getString(resId);
    }

    public String getString(int resId, Object... objects) {
        return context.getString(resId, objects);
    }

    public String[] getStringArray(int resId) {
        return context.getResources().getStringArray(resId);
    }

    public Drawable getDrawable(int resId) {
        return ContextCompat.getDrawable(context, resId);
    }

    public int getColor(int resId) {
        return ContextCompat.getColor(context, resId);
    }

    public Locale getCurrentLocale() {
        return Utils.getCurrentLocale(context);
    }

}
