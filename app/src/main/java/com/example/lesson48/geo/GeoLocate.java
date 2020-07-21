package com.example.lesson48.geo;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Looper;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;

import com.example.lesson48.MainActivity;
import com.example.lesson48.utils.MapUtils;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.GeofencingClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.io.IOException;
import java.util.List;

public class GeoLocate {
    private static final String TAG = "GeoLocate";

    private Activity activity;
    private LocationRequest locationRequest;
    private LocationCallback locationCallback;
    private Location location;
    private FusedLocationProviderClient client;


    public GeoLocate(Activity activity) {
        this.activity = activity;
    }


    private Address getLocation(double lat, double lng) throws IOException {
        Geocoder geocoder = new Geocoder(activity);
        List<Address> addresses = geocoder.getFromLocation(lat, lng, 1);
        if (!addresses.isEmpty()) {
            return addresses.get(0);
        }
        return null;
    }

    private Address getLocation(String locationName) throws IOException {
        Geocoder geocoder = new Geocoder(activity);
        List<Address> addresses = geocoder.getFromLocationName(locationName, 1);
        if (!addresses.isEmpty()) {
            return addresses.get(0);
        }
        return null;
    }

    public Address getAddress(String locationName) {
        try {
            return getLocation(locationName);
        } catch (IOException e) {
            Log.d(TAG, "getAddress: IOException " + e.getMessage());
        }
        return null;
    }

    public Address getAddress(double lat, double lng) {
        try {
            return getLocation(lat, lng);
        } catch (IOException e) {
            Log.d(TAG, "getAddress: IOException " + e.getMessage());
        }
        return null;
    }


    public Task<Location> getTask() {
        FusedLocationProviderClient fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(activity);
        if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            return null;
        }
        return fusedLocationProviderClient.getLastLocation();
    }




}
