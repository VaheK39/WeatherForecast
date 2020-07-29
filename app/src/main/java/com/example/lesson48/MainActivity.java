package com.example.lesson48;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.WindowManager;
import android.widget.Toast;

import com.example.lesson48.connection.ConnectionState;
import com.example.lesson48.connection.NetworkConnection;
import com.example.lesson48.fragment.FindCityFragment;
import com.example.lesson48.fragment.ForecastDataUpdater;
import com.example.lesson48.fragment.FragmentReplacer;
import com.example.lesson48.fragment.LoadingFragment;
import com.example.lesson48.fragment.NoConnectionFragment;
import com.example.lesson48.fragment.SplashScreenFragment;
import com.example.lesson48.fragment.ViewPagerFragment;
import com.example.lesson48.geo.GeoLocate;
import com.example.lesson48.model.FiveDayWeatherForecast;
import com.example.lesson48.permission.PermissionsHandler;
import com.example.lesson48.retrofit.ForecastGetter;
import com.example.lesson48.retrofit.RetrofitClient;
import com.example.lesson48.utils.Constants;
import com.example.lesson48.utils.MapUtils;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.Task;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity
        implements ForecastGetter, FindCityFragment.OnFindFromMapListener, NoConnectionFragment.Refresher {
    private static final String TAG = "MainActivity";

    private RetrofitClient client;
    private PermissionsHandler handler;
    private LocationCallback locationCallback;
    private ForecastDataUpdater forecastDataUpdater;
    private ConnectionState connectionState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setStatusBarColor();
        showSplash();


        connectionState = new ConnectionState(this);
        new Handler().postDelayed(this::checkInternetConnection, 2000);


    }


    private void checkInternetConnection() {
        if (NetworkConnection.isConnectedToInternet(this)) {
            runApplication();
        } else {
            FragmentReplacer.replace(this, R.id.main_container, new NoConnectionFragment(), getString(R.string.no_connection_fragment_tag));
        }
    }

    public void runApplication() {
        FragmentReplacer.replace(this, R.id.main_container, new LoadingFragment(), getString(R.string.loading_fragment_tag));
        handler = new PermissionsHandler(this, MapUtils.MAP_PERMISSIONS);
        handler.create();
        client = new RetrofitClient(this);
        RetrofitClient.create();
        getForecastOfYourLocation();

    }

    @Override
    protected void onResume() {
        super.onResume();
        connectionState.enable(this);

    }

    @Override
    protected void onPause() {
        super.onPause();
        connectionState.disable(this);
    }

    private void getForecastOfYourLocation() {
        GeoLocate geoLocate = new GeoLocate(this);
        Task<Location> locationTask = geoLocate.getTask();
        if (locationTask == null) {
            return;
        }
        locationTask.addOnSuccessListener(location -> {
            if (location != null) {
                getCallData(location);

            } else {
                makeLocationRequest();
            }
        });


    }

    private void makeLocationRequest() {
        LocationRequest locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(MapUtils.LOCATION_REQUEST_INTERVAL);
        locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                super.onLocationResult(locationResult);
                if (locationResult != null) {
                    for (Location location : locationResult.getLocations()) {
                        if (location != null) {
                            getCallData(location);
                            return;
                        }
                    }
                }
            }
        };
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        LocationServices.getFusedLocationProviderClient(this)
                .requestLocationUpdates(locationRequest, locationCallback, Looper.getMainLooper());
    }

    private void getCallData(Location location) {
        Map<String, String> queries = new HashMap<>();
        queries.put(Constants.MapQueries.KEY_LAT, String.valueOf(location.getLatitude()));
        queries.put(Constants.MapQueries.KEY_LNG, String.valueOf(location.getLongitude()));
        client.getForecast(queries, Constants.CallCases.CALL_FROM_MAIN);
        if (locationCallback != null)
            LocationServices.getFusedLocationProviderClient(this)
                    .removeLocationUpdates(locationCallback);
    }


    @Override
    public void setForecast(FiveDayWeatherForecast forecast, String calledFrom) {
        switch (calledFrom) {
            case Constants.CallCases.CALL_FROM_MAIN:
                //replaceFragment(R.id.main_container, ViewPagerFragment.newInstance(forecast), getString(R.string.vp_fragment_tag));
                FragmentReplacer.replace(this, R.id.main_container, ViewPagerFragment.newInstance(forecast), getString(R.string.vp_fragment_tag));
                break;
            case Constants.CallCases.CALL_FROM_MAP:
                forecastDataUpdater = (ForecastDataUpdater) getSupportFragmentManager().findFragmentByTag(getString(R.string.vp_fragment_tag));
                assert forecastDataUpdater != null;
                forecastDataUpdater.onForecastUpdate(forecast);
                break;
        }

    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PermissionsHandler.PERMISSION_REQUEST_CODE) {

            for (int i = 0; i < permissions.length; i++) {
                String permission = permissions[i];

                if (grantResults[i] == PackageManager.PERMISSION_DENIED) {
                    if (!shouldShowRequestPermissionRationale(permission)) {
                        handler.showPermissionDeniedDialog(true);
                        return;
                    }
                    handler.showPermissionDeniedDialog(false);
                    return;
                }
            }
            runApplication();
        }
    }

    private void setStatusBarColor() {
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS,
                WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        getWindow().setStatusBarColor(getResources().getColor(R.color.colorBlueSky, null));
    }


    private void showSplash() {
        FragmentReplacer.replace(this, R.id.main_container, new SplashScreenFragment(), getString(R.string.splash_screen_tag));
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == MapUtils.MAP_INTENT_RESULT_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                assert data != null;
                String latitude = String.valueOf(data.getDoubleExtra(MapUtils.MAP_INTENT_RESULT_KEY_LATITUDE, 0));
                String longitude = String.valueOf(data.getDoubleExtra(MapUtils.MAP_INTENT_RESULT_KEY_LONGITUDE, 0));
                Map<String, String> queries = new HashMap<>();
                queries.put(Constants.MapQueries.KEY_LAT, latitude);
                queries.put(Constants.MapQueries.KEY_LNG, longitude);
                client.getForecast(queries, Constants.CallCases.CALL_FROM_MAP);
            }
        }
    }

    @Override
    public void onFindFromMapClick() {
        startActivityForResult(new Intent(this, MapActivity.class), MapUtils.MAP_INTENT_RESULT_REQUEST_CODE);
        overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
    }

    @Override
    public void onRefresh() {
        if (NetworkConnection.isConnectedToInternet(this)) {
            runApplication();
        } else {
            Toast.makeText(this, "No Connection", Toast.LENGTH_SHORT).show();
        }
    }
}