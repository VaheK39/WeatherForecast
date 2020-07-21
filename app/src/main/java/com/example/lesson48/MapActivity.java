package com.example.lesson48;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Toast;

import com.example.lesson48.geo.GeoLocate;
import com.example.lesson48.utils.MapUtils;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.Task;

import static com.example.lesson48.utils.MapUtils.LOCATION_PERMISSION_REQUEST_CODE;

public class MapActivity extends AppCompatActivity
        implements OnMapReadyCallback, View.OnClickListener {
    private static final String TAG = "MapActivity";

    private GoogleMap map;
    private boolean locationPermissionsGranted = false;
    private double latitude, longitude;

    private AppCompatEditText edtSearch;
    private AppCompatImageView btnLocate, btnSelectThisLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

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


    private void initSearchBar() {
        edtSearch.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_SEARCH
                    || actionId == EditorInfo.IME_ACTION_DONE
                    || event.getAction() == KeyEvent.KEYCODE_ENTER
                    || event.getAction() == KeyEvent.ACTION_DOWN) {

                geoLocate();
            }
            return false;
        });
    }

    private void geoLocate() {
        String searchString = String.valueOf(edtSearch.getText());
        GeoLocate geoLocate = new GeoLocate(this);
        Address address = geoLocate.getAddress(searchString);
        if (address == null) {
            Toast.makeText(this, R.string.invalid_address, Toast.LENGTH_SHORT).show();
            return;
        }
        Log.d(TAG, "geoLocate: found an address: " + address.getAddressLine(0));
        moveCamera(new LatLng(address.getLatitude(), address.getLongitude()),
                MapUtils.DEFAULT_ZOOM,
                address.getAddressLine(0));
        latitude = address.getLatitude();
        longitude = address.getLongitude();

    }

    private void getDeviceLocation() {
        Log.d(TAG, "getDeviceLocation: getting device's location");
        GeoLocate geoLocate = new GeoLocate(this);
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

                Address address = geoLocate.getAddress(latitude, longitude);
                Log.d(TAG, "onComplete: locationName = " + address.getAddressLine(0));
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
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                break;
        }
    }
}