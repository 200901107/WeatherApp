package com.example.vivek.weather.location;

import android.location.Location;
import android.os.Bundle;
import android.support.annotation.Nullable;

public interface ILocationUpdates {

    void onLocationChanged(Location var1);

    void onConnected(@Nullable Bundle var1);
}
