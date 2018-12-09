package com.example.vivek.weather.assertions;

import android.support.test.espresso.NoMatchingViewException;
import android.support.test.espresso.ViewAssertion;
import android.view.View;
import android.widget.TextView;

import static org.hamcrest.Matchers.both;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.lessThan;
import static org.junit.Assert.assertThat;

public class ValidTemperatureAssertion implements ViewAssertion {

    // temperature constants to check if valid temperature is displayed, can be changes to a realistic data
    private static final int MIN_TEMPERATURE = -50;
    private static final int MAX_TEMPERATURE = 100;


    public ValidTemperatureAssertion() { }

    @Override
    public void check(View view, NoMatchingViewException noViewFoundException) {
        if (noViewFoundException != null || view == null) {
            throw noViewFoundException;
        }

        Double temp = Double.parseDouble(((TextView)view).getText().toString().replaceAll("[\\D]", ""));
        System.out.print(temp);

        assertThat(temp.intValue(), both(greaterThan(MIN_TEMPERATURE)).and(lessThan(MAX_TEMPERATURE)));
    }
}
