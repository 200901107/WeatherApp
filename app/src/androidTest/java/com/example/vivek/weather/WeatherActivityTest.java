package com.example.vivek.weather;

import android.support.test.espresso.IdlingRegistry;
import android.support.test.rule.ActivityTestRule;
import android.support.test.rule.GrantPermissionRule;
import android.support.test.runner.AndroidJUnit4;

import com.example.vivek.weather.assertions.RecyclerViewItemCountAssertion;
import com.example.vivek.weather.assertions.ValidTemperatureAssertion;
import com.example.vivek.weather.utils.TestUtils;
import com.example.vivek.weather.utils.espressoutils.EspressoIdlingResource;
import com.example.vivek.weather.weather.WeatherActivity;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;

@RunWith(AndroidJUnit4.class)
public class WeatherActivityTest {

    private static final int WEATHER_FORECAST_DAYS = 4;

    @Rule
    public ActivityTestRule<WeatherActivity> mActivityRule =
            new ActivityTestRule<>(WeatherActivity.class);

    @Rule
    public GrantPermissionRule fineLocationRule = GrantPermissionRule.grant(android.Manifest.permission.ACCESS_FINE_LOCATION);

    @Rule
    public GrantPermissionRule courseLocationrule = GrantPermissionRule.grant(android.Manifest.permission.ACCESS_COARSE_LOCATION);

    @Before
    public void registerIdlingResource() {
        IdlingRegistry.getInstance().register(EspressoIdlingResource.getIdlingResource());
    }

    @After
    public void unregisterIdlingResource() {
        IdlingRegistry.getInstance().unregister(EspressoIdlingResource.getIdlingResource());
    }

    @Test
    public void testViews() {
        onView(withId(R.id.current_temp)).check(matches(isDisplayed()));
        onView(withId(R.id.region)).check(matches(isDisplayed()));
    }

    // could be optimized
    @Test
    public void testValidTemperature() {
        onView(TestUtils.withRecyclerView(R.id.forecastList).atPositionOnView(0, R.id.tempTextView)).check(new ValidTemperatureAssertion());
        onView(TestUtils.withRecyclerView(R.id.forecastList).atPositionOnView(1, R.id.tempTextView)).check(new ValidTemperatureAssertion());
        onView(TestUtils.withRecyclerView(R.id.forecastList).atPositionOnView(2, R.id.tempTextView)).check(new ValidTemperatureAssertion());
        onView(TestUtils.withRecyclerView(R.id.forecastList).atPositionOnView(3, R.id.tempTextView)).check(new ValidTemperatureAssertion());
    }

    @Test
    public void testNumberOfForecastDays() {
        onView(withId(R.id.forecastList)).check(new RecyclerViewItemCountAssertion(WEATHER_FORECAST_DAYS));
    }

}
