package com.example.vivek.weather.modules.weather;


import com.example.vivek.weather.application.components.ResourceProvider;
import com.example.vivek.weather.application.schedulers.SchedulerProvider;
import com.example.vivek.weather.weather.WeatherContract;
import com.example.vivek.weather.weather.WeatherInteractor;
import com.example.vivek.weather.weather.WeatherPresenter;
import com.example.vivek.weather.weather.models.ForecastModel;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.powermock.modules.junit4.PowerMockRunner;

import io.reactivex.Observable;
import io.reactivex.schedulers.TestScheduler;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(PowerMockRunner.class)
public class WeatherPresenterTest {

    @Mock
    private SchedulerProvider mSchedulerProvider;

    @Mock
    private ResourceProvider resProvider;

    @Mock
    private ForecastModel forecastModel;

    @Mock
    private WeatherContract.View mView;

    @Mock
    private WeatherInteractor mInteractor;

    @InjectMocks
    private WeatherPresenter mPresenter;

    @BeforeClass
    public static void start() {

    }

    private TestScheduler testScheduler = new TestScheduler();

    @Before
    public void setupBefore(){
        when(mSchedulerProvider.ui()).thenReturn(testScheduler);
        when(mSchedulerProvider.io()).thenReturn(testScheduler);
        mPresenter.subscribeWithView(mView);
    }

    @Test
    public void testFetchSuccessForecastData(){
        doReturn(Observable.just(forecastModel)).when(mInteractor).fetchWeatherForeCasts(anyString(), anyString());
        mPresenter.fetchWeatherForeCasts(anyString(), anyString());
        testScheduler.triggerActions();
        verify(mView, times(1)).onWeatherForeCastFetched(forecastModel);
    }

    @Test
    public void testForecastFailureCase() {
        mPresenter.fetchWeatherForeCasts(null, null);
        testScheduler.triggerActions();
        verify(mView, times(1)).onWeatherFetchFailed();
    }

}
