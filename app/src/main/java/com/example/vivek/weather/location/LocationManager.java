package com.example.vivek.weather.location;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.example.vivek.weather.application.AppApplication;

import android.location.Location;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.TimeUnit;

@SuppressLint("MissingPermission")
public class LocationManager implements LocationListener, GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener {

    private Set<ILocationUpdates> mLocationListeners;

    private volatile GoogleApiClient mGoogleApiClient;

    private volatile LocationRequest mLocationRequest;

    private static final int DEFAULT_UPDATE_INTERVAL = 10;

    private static final int DEFAULT_FASTEST_INTERVAL = 16;

    private static final int DEFAULT_SMALLEST_DISP = 4;

    private static volatile LocationManager instance;

    private String TAG = getClass().getSimpleName();

    private LocationManager() {
        this.mLocationListeners = new CopyOnWriteArraySet<ILocationUpdates>(); //Default size as 1.
    }

    public static LocationManager getInstance() {
        if (instance == null) {
            synchronized (LocationManager.class) {
                if (instance == null) {
                    instance = new LocationManager();
                }
            }
        }

        return instance;
    }

    private synchronized void init() {
        if (mGoogleApiClient == null) {
            mGoogleApiClient = new GoogleApiClient.Builder(
                    AppApplication.getInstance().getApplicationContext())
                    .addApi(LocationServices.API).addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this).build();
        }

        if (mLocationRequest == null) {
            mLocationRequest = LocationRequest.create().setInterval(DEFAULT_UPDATE_INTERVAL)
                    // 1 seconds
                    .setFastestInterval(DEFAULT_FASTEST_INTERVAL)
                    // 16ms = 60fps
                    .setSmallestDisplacement(DEFAULT_SMALLEST_DISP)
                    .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        }
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        Log.d(TAG, "onConnected : ");

        for (ILocationUpdates listener : mLocationListeners) {
            listener.onConnected(bundle);
        }

        startLocationUpdates();
    }

    /**
     * Reason can be : <br>
     * int CAUSE_SERVICE_DISCONNECTED = 1;<br>
     * int CAUSE_NETWORK_LOST = 2;
     *
     * @param reason
     */
    @Override
    public void onConnectionSuspended(int reason) {
        Log.d(TAG, "onConnectionSuspended : " + reason);
        stopLocationUpdates();
    }

    /**
     * Called when the location has changed.
     * <p/>
     * <p> There are no restrictions on the use of the supplied Location object.
     *
     * @param location The new location, as a Location object.
     */
    @Override
    public void onLocationChanged(Location location) {
        Log.d(TAG, "onLocationChanged : ");

        for (ILocationUpdates listener : mLocationListeners) {
            listener.onLocationChanged(location);
        }
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.d(TAG, "onConnectionFailed : ");
    }

    public void addLocationListener(ILocationUpdates listener) {
        if (listener != null) {
            mLocationListeners.add(listener);
        }

        connectToLocation();
    }

    public void removeLocationListener(ILocationUpdates listener) {
        if (listener != null) {
            mLocationListeners.remove(listener);
        }
    }

    private void startLocationUpdates() {
        init();
        LocationServices.FusedLocationApi
                .requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
    }

    private void stopLocationUpdates() {
        init();
        LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
    }

    public void connectToLocation() {
        init();
        mGoogleApiClient.connect();
    }

    public void disConnectFromLocation() {
        init();
        if (isGoogleAPIClientConnected()) {
            stopLocationUpdates();
            mGoogleApiClient.disconnect();
        }
    }

    public Location getLastLocation() {
        connectToLocation();
        return LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
    }

    public boolean isGoogleAPIClientConnected() {
        if(mGoogleApiClient != null) {
            return mGoogleApiClient.isConnected();
        }
        return false;
    }

    public Location getLastLocationBlocking() {
        init();
        mGoogleApiClient.blockingConnect(1, TimeUnit.SECONDS);

        if(isGoogleAPIClientConnected()) {
            Location lastKnownLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
            LocationManager.getInstance().disConnectFromLocation();
            return lastKnownLocation;
        }

        return null;
    }

    /**
     * Custom Method to set the custom Location Request
     *  Adding parameters of enableHighAccuracy
     */
    public void setCustomLocationRequest(boolean enableHighAccuracy,int timeInterval ){
        int accuracy = enableHighAccuracy ? LocationRequest.PRIORITY_HIGH_ACCURACY :LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY;
        mLocationRequest = LocationRequest.create().setInterval(timeInterval)
                // 1 seconds
                .setFastestInterval(DEFAULT_FASTEST_INTERVAL)
                // 16ms = 60fps
                .setSmallestDisplacement(DEFAULT_SMALLEST_DISP)
                .setPriority(accuracy);
    }

    public LocationRequest getLocationRequest() {
        if(mLocationRequest==null){
            mLocationRequest = LocationRequest.create().setInterval(DEFAULT_UPDATE_INTERVAL)
                    // 1 seconds
                    .setFastestInterval(DEFAULT_FASTEST_INTERVAL)
                    // 16ms = 60fps
                    .setSmallestDisplacement(DEFAULT_SMALLEST_DISP)
                    .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        }

        return mLocationRequest;
    }

}
