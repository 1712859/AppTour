package com.example.tourviet;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class MapGetLocationActivity extends FragmentActivity implements OnMapReadyCallback {
    static final String TAG = "MapGetLocationActivity";
    static final int SEARCH_LOCATION_REQUEST_CODE = 5555;
    static final int FINE_LOCATION_PERMISSION_REQUEST_CODE = 1111;
    static final int COARSE_LOCATION_PERMISSION_REQUEST_CODE = 2222;
    Button searchBtn;
    Button confirmBtn;
    GoogleMap myMap;

    FusedLocationProviderClient fusedLocationProviderClient;
    double las, lng;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_get_location);

        getPerMission(Manifest.permission.ACCESS_FINE_LOCATION, FINE_LOCATION_PERMISSION_REQUEST_CODE);
        getPerMission(Manifest.permission.ACCESS_COARSE_LOCATION, COARSE_LOCATION_PERMISSION_REQUEST_CODE);

        if (!Places.isInitialized()) {
            Places.initialize(getApplicationContext(), getResources().getString(R.string.google_maps_key));
        }

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.mapGetLocation_mapFragment);
        mapFragment.getMapAsync(this);

        searchBtn = findViewById(R.id.mapGetLocation_searchBtn);
        confirmBtn = findViewById(R.id.mapGetLocation_confirmBtn);

        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<Place.Field> fields = Arrays.asList(Place.Field.ID, Place.Field.LAT_LNG, Place.Field.NAME);
                Intent autoCompleteIntent = new Autocomplete.IntentBuilder(AutocompleteActivityMode.OVERLAY, fields).build(getApplicationContext());
                startActivityForResult(autoCompleteIntent, SEARCH_LOCATION_REQUEST_CODE);
            }
        });

        confirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LatLng centerLatLang = myMap.getProjection().getVisibleRegion().latLngBounds.getCenter();
                las = centerLatLang.latitude;
                lng = centerLatLang.longitude;

                String name = "???";
                try {
                    Geocoder geocoder = new Geocoder(MapGetLocationActivity.this);
                    List<Address> addressList = geocoder.getFromLocation(las, lng, 1);
                    name = addressList.get(0).getAddressLine(0);
                } catch (IOException e) {
                    Log.e("IOException: ", e.getMessage());
                }

                Intent intent = new Intent();
                intent.putExtra("name", name);
                intent.putExtra("las", las);
                intent.putExtra("long", lng);
                setResult(RESULT_OK, intent);
                onBackPressed();
            }
        });
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        myMap = googleMap;
        myMap.setMyLocationEnabled(true);
        myMap.getUiSettings().setCompassEnabled(true);
        setUserLocation();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode==RESULT_OK){
            if (requestCode==SEARCH_LOCATION_REQUEST_CODE){
                if(data!=null){
                    Place place = Autocomplete.getPlaceFromIntent(data);
                    LatLng selectedLocation = place.getLatLng();
                    myMap.moveCamera(CameraUpdateFactory.newLatLngZoom(selectedLocation, 15f));
                }
            }
        }
    }

    private void getPerMission(String permission, int requestCode) {
        if (ActivityCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(MapGetLocationActivity.this, permission)) {
            } else {
                ActivityCompat.requestPermissions(MapGetLocationActivity.this, new String[]{permission}, requestCode);
            }
        }
    }

    private void setUserLocation() {
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        try {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                Task location = fusedLocationProviderClient.getLastLocation();
                location.addOnCompleteListener(new OnCompleteListener() {
                    @Override
                    public void onComplete(@NonNull Task task) {
                        if (task.isSuccessful()) {
                            Location currentLocation = (Location) task.getResult();
                            LatLng userLocation = new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude());
                            myMap.addMarker(new MarkerOptions().position(userLocation).title("Vị Trí hiện tại"));
                            myMap.moveCamera(CameraUpdateFactory.newLatLngZoom(userLocation, 15f));
                        } else {
                            Toast.makeText(MapGetLocationActivity.this, "Không thể lấy được vị trí hiện tại", Toast.LENGTH_LONG).show();
                        }
                    }
                });

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
