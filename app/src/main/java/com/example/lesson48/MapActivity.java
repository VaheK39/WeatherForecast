package com.example.lesson48;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatAutoCompleteTextView;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.lesson48.customized_UI.ActivityUI;
import com.example.lesson48.geo.GeoLocate;
import com.example.lesson48.utils.MapUtils;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.Task;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener;

import java.util.Arrays;
import java.util.Objects;

import static com.example.lesson48.utils.MapUtils.DEFAULT_ZOOM;
import static com.example.lesson48.utils.MapUtils.LOCATION_PERMISSION_REQUEST_CODE;

public class MapActivity extends AppCompatActivity
        implements OnMapReadyCallback, View.OnClickListener, PlaceSelectionListener {
    private static final String TAG = "MapActivity";


    private GoogleMap map;
    private GeoLocate geoLocate;
    private boolean locationPermissionsGranted = false;
    private double latitude, longitude;
    private AppCompatAutoCompleteTextView edtSearch;
    private AppCompatImageView btnLocate, btnSelectThisLocation;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        ActivityUI.setStatusBarColor(this, R.color.colorBlueSky);

        findIds();
        getLocationPermissions();
        initButtonClicks();
    }



    private void findIds() {
        edtSearch = findViewById(R.id.edt_search);
        btnLocate = findViewById(R.id.btn_locate);
        btnSelectThisLocation = findViewById(R.id.btn_select_this_location);
    }


    private void initButtonClicks() {
        btnLocate.setOnClickListener(this);
        btnSelectThisLocation.setOnClickListener(this);
    }


    private void getLocationPermissions() {
        if (ContextCompat.checkSelfPermission(this, MapUtils.MAP_PERMISSIONS[0]) == PackageManager.PERMISSION_GRANTED) {
            if (ContextCompat.checkSelfPermission(this, MapUtils.MAP_PERMISSIONS[1]) == PackageManager.PERMISSION_GRANTED) {
                locationPermissionsGranted = true;
                initMap();
            } else {
                ActivityCompat.requestPermissions(this, MapUtils.MAP_PERMISSIONS, LOCATION_PERMISSION_REQUEST_CODE);
            }
        } else {
            ActivityCompat.requestPermissions(this, MapUtils.MAP_PERMISSIONS, LOCATION_PERMISSION_REQUEST_CODE);
        }
    }


    private void initMap() {
        geoLocate = new GeoLocate(this);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_map);
        assert mapFragment != null;
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        Log.d(TAG, "onMapReady: Map is ready");

        if (locationPermissionsGranted) {
            getDeviceLocation();
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED &&
                    ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                            != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            map.setMyLocationEnabled(true);
            map.getUiSettings().setMyLocationButtonEnabled(false);

            initSearchBar();

        }
    }


    private void getDeviceLocation() {
        Log.d(TAG, "getDeviceLocation: getting device's location");
        Task<Location> location = geoLocate.getTask();

        location.addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Log.d(TAG, "onComplete: Found location");
                Location currentLocation = task.getResult();
                assert currentLocation != null;
                latitude = currentLocation.getLatitude();
                longitude = currentLocation.getLongitude();
                moveCamera(new LatLng(latitude, longitude),
                        MapUtils.DEFAULT_ZOOM, MapUtils.MY_LOCATION_TITLE);

            } else {
                Log.d(TAG, "onComplete: Current location is null");
                Toast.makeText(MapActivity.this, R.string.unable_to_get_current_location, Toast.LENGTH_SHORT).show();
            }
        });

    }



    private void moveCamera(LatLng latLng, float zoom, String title) {
        Log.d(TAG, "moveCamera: moving the camera to: lat: " + latLng.latitude + ", lng: " + latLng.longitude);
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoom));
        if (!title.equals(MapUtils.MY_LOCATION_TITLE)) {
            MarkerOptions markerOptions = new MarkerOptions()
                    .position(latLng)
                    .title(title);
            map.addMarker(markerOptions);
        }
    }




    private void initSearchBar() {
        Places.initialize(getApplicationContext(), getString(R.string.google_maps_api_key));
        searchAnAddress();

    }


    private void searchAnAddress() {
        AutocompleteSupportFragment autocompleteSupportFragment = (AutocompleteSupportFragment)
                getSupportFragmentManager()
                        .findFragmentById(R.id.fragment_places);
        assert autocompleteSupportFragment != null;
        autocompleteSupportFragment.setPlaceFields(Arrays.asList(Place.Field.LAT_LNG, Place.Field.NAME));
        autocompleteSupportFragment.setOnPlaceSelectedListener(this);
    }

    @Override
    public void onPlaceSelected(@NonNull Place place) {
        Log.d(TAG, "onPlaceSelected: Selected:" + place.getName() +
                "\nlat = " + Objects.requireNonNull(place.getLatLng()).latitude +
                ", lng = " + place.getLatLng().longitude);
        Log.d(TAG, "onPlaceSelected: ");
        this.latitude = place.getLatLng().latitude;
        this.longitude = place.getLatLng().longitude;
        moveCamera(place.getLatLng(), DEFAULT_ZOOM, Objects.requireNonNull(place.getName()));
    }

    @Override
    public void onError(@NonNull Status status) {
        Log.d(TAG, "onError: Failed to access:"  + status.getStatusMessage());
    }



    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        locationPermissionsGranted = false;
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            for (int res : grantResults) {
                if (res == PackageManager.PERMISSION_DENIED) {
                    return;
                }
            }
            locationPermissionsGranted = true;
            initMap();
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_locate:
                getDeviceLocation();
                break;
            case R.id.btn_select_this_location:
                Intent data = new Intent();
                data.putExtra(MapUtils.MAP_INTENT_RESULT_KEY_LATITUDE, latitude);
                data.putExtra(MapUtils.MAP_INTENT_RESULT_KEY_LONGITUDE, longitude);
                setResult(RESULT_OK, data);
                finish();
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.slide_out_right);
                break;
        }
    }

}