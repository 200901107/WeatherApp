package com.example.vivek.weather.utils;

import android.content.Context;
import android.os.Build;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Utils {

    public static final String DATE_FORMAT_YYYY_MM_DD = "yyyy-MM-dd";

    public static Locale getCurrentLocale(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            return context.getResources().getConfiguration().getLocales().get(0);
        } else {
            //no inspection deprecation
            return context.getResources().getConfiguration().locale;
        }
    }

    public static String getDayForDate(String inputDate, Locale locale) {
        SimpleDateFormat inputDateFormat = new SimpleDateFormat(DATE_FORMAT_YYYY_MM_DD, locale);
        Date date = null;
        try {
            date = inputDateFormat.parse(inputDate);
        } catch (ParseException e) {
            e.printStackTrace();
            return "";
        }
        return inputDateFormat.format(date);
    }
}
