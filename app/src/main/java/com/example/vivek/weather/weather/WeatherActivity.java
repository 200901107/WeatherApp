package com.example.vivek.weather.weather;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import com.example.vivek.weather.R;
import com.example.vivek.weather.application.AppApplication;
import com.example.vivek.weather.application.BaseActivity;
import com.example.vivek.weather.weather.adapter.WeatherAdapter;
import com.example.vivek.weather.weather.models.DayForeCastModel;

import java.util.List;

import javax.inject.Inject;

public class WeatherActivity extends BaseActivity implements WeatherContract.View{

    @Inject
    public WeatherPresenter weatherPresenter;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.weather_activity);
        initViews();
    }

    @Override
    protected void setupActivityComponent() {
        WeatherComponent component = DaggerWeatherComponent.builder()
                .applicationComponent(AppApplication.getInstance().getComponent())
                .weatherModule(new WeatherModule(this))
                .build();
        component.inject(this);
    }

    private void initViews() {
        recyclerView = findViewById(R.id.forecastList);
    }

    @Override
    public void onWeatherForeCastFetched(List<DayForeCastModel> dayForeCastModels) {
        recyclerView.setAdapter(new WeatherAdapter(dayForeCastModels, this));
    }
}
