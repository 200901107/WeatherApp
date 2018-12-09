package com.example.vivek.weather.weather;

import android.Manifest;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ProgressBar;

import com.example.vivek.weather.R;
import com.example.vivek.weather.application.AppApplication;
import com.example.vivek.weather.application.BaseActivity;
import com.example.vivek.weather.location.ILocationUpdates;
import com.example.vivek.weather.location.LocationManager;
import com.example.vivek.weather.utils.espressoutils.EspressoIdlingResource;
import com.example.vivek.weather.utils.Utils;
import com.example.vivek.weather.weather.adapter.WeatherAdapter;
import com.example.vivek.weather.weather.models.DayForeCastModel;
import com.example.vivek.weather.weather.models.ForecastModel;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class WeatherActivity extends BaseActivity implements WeatherContract.View, ILocationUpdates, View.OnClickListener{

    private static final int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;
    private static final int ALL_PERMISSIONS_RESULT = 1011;

    @Inject
    public WeatherPresenter weatherPresenter;
    private RecyclerView recyclerView;
    private View errorLayout;
    private ProgressBar progressBar;
    private View contentView;

    // lists for permissions
    private ArrayList<String> permissionsToRequest;
    private ArrayList<String> permissionsRejected = new ArrayList<>();
    private ArrayList<String> permissions = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.weather_activity);
        initViews();
        weatherPresenter.subscribeWithView(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (hasPermission(Manifest.permission.ACCESS_FINE_LOCATION) || hasPermission(Manifest.permission.ACCESS_COARSE_LOCATION)) {
            LocationManager.getInstance().addLocationListener(this);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
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
        recyclerView.setBackground(Utils.generateBackgroundWithShadow(recyclerView, R.color.white,
                R.dimen.card_view_radius, R.color.colorPrimaryDark, R.dimen.elevation, Gravity.TOP));
        errorLayout = findViewById(R.id.error_layout);
        contentView = findViewById(R.id.contentView);

        View retryButton = findViewById(R.id.retryButton);
        retryButton.setOnClickListener(this);

        progressBar = findViewById(R.id.progress);
        progressBar.setIndeterminate(true);
        progressBar.setVisibility(View.VISIBLE);

        permissions.add(Manifest.permission.ACCESS_FINE_LOCATION);
        permissions.add(Manifest.permission.ACCESS_COARSE_LOCATION);

        permissionsToRequest = permissionsToRequest(permissions);

        checkLocationPermissions();
    }

    private void checkLocationPermissions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (permissionsToRequest.size() > 0) {
                requestPermissions(permissionsToRequest.
                        toArray(new String[permissionsToRequest.size()]), ALL_PERMISSIONS_RESULT);
            }
        }
    }

    private ArrayList<String> permissionsToRequest(ArrayList<String> wantedPermissions) {
        ArrayList<String> result = new ArrayList<>();

        for (String perm : wantedPermissions) {
            if (!hasPermission(perm)) {
                result.add(perm);
            }
        }

        return result;
    }

    private boolean hasPermission(String permission) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            return checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED;
        }

        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch(requestCode) {
            case ALL_PERMISSIONS_RESULT:
                for (String perm : permissionsToRequest) {
                    if (!hasPermission(perm)) {
                        permissionsRejected.add(perm);
                    }
                }

                if (permissionsRejected.size() > 0) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        if (shouldShowRequestPermissionRationale(permissionsRejected.get(0))) {
                            new AlertDialog.Builder(WeatherActivity.this).
                                    setMessage("These permissions are mandatory to get your location. You need to allow them.").
                                    setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                                requestPermissions(permissionsRejected.
                                                        toArray(new String[permissionsRejected.size()]), ALL_PERMISSIONS_RESULT);
                                            }
                                        }
                                    }).setNegativeButton("Cancel", null).create().show();

                            return;
                        }
                    }

                } else {
                    LocationManager.getInstance().addLocationListener(this);
                }

                break;
        }
    }

    private boolean checkPlayServices() {
        GoogleApiAvailability apiAvailability = GoogleApiAvailability.getInstance();
        int resultCode = apiAvailability.isGooglePlayServicesAvailable(this);

        if (resultCode != ConnectionResult.SUCCESS) {
            if (apiAvailability.isUserResolvableError(resultCode)) {
                apiAvailability.getErrorDialog(this, resultCode, PLAY_SERVICES_RESOLUTION_REQUEST);
            } else {
                finish();
            }

            return false;
        }

        return true;
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (checkPlayServices())
            LocationManager.getInstance().removeLocationListener(this);
    }

    @Override
    public void onWeatherForeCastFetched(ForecastModel forecastModel) {
        progressBar.setIndeterminate(false);
        progressBar.setVisibility(View.GONE);
        errorLayout.setVisibility(View.GONE);
        contentView.setVisibility(View.VISIBLE);
        Animation animation = AnimationUtils.loadAnimation(WeatherActivity.this, R.anim.translate_anim);
        if (forecastModel != null) {
            List<DayForeCastModel> dayForeCastModels = forecastModel.getForeCastModel().getDayForeCastModels();

            if (forecastModel.getLocationModel() != null && !TextUtils.isEmpty(forecastModel.getLocationModel().getLocation()))
                ((AppCompatTextView) findViewById(R.id.region)).setText(forecastModel.getLocationModel().getLocation());

            if (dayForeCastModels != null && dayForeCastModels.size() > 0) {
                ((AppCompatTextView) findViewById(R.id.current_temp)).setText(getString(R.string.current_temp, dayForeCastModels.get(0).getForeCastDetails().getAvgTemperature()));
                recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
                recyclerView.setAdapter(new WeatherAdapter(dayForeCastModels.subList(1, 5), this));
                recyclerView.postDelayed(() -> {
                    recyclerView.setVisibility(View.VISIBLE);
                    recyclerView.startAnimation(animation);
                }, 500);
            }
            EspressoIdlingResource.decrement();
        }

    }

    @Override
    public void onWeatherFetchFailed() {
        progressBar.setIndeterminate(false);
        contentView.setVisibility(View.GONE);
        progressBar.setVisibility(View.GONE);
        errorLayout.setVisibility(View.VISIBLE);
        EspressoIdlingResource.decrement();
    }

    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onConnected(@Nullable Bundle var1) {
        fetchForecast();
    }

    private void fetchForecast() {
        EspressoIdlingResource.increment();
        if (weatherPresenter != null && LocationManager.getInstance().getLastLocation() != null) {
            double latitude = LocationManager.getInstance().getLastLocation().getLatitude();
            double longitude = LocationManager.getInstance().getLastLocation().getLongitude();
            weatherPresenter.fetchWeatherForeCasts(getString(R.string.location_parameter, latitude, longitude), Utils.NUM_OF_DAYS_OF_FORECAST);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (weatherPresenter != null)
            weatherPresenter.unsubscribeWithView();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.retryButton:
                retryForecastFetch();
                break;
        }
    }

    private void retryForecastFetch() {
        fetchForecast();
    }
}
