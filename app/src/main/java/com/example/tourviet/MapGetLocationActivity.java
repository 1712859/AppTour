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

import java.io.IOException;
import java.util.List;

public class MapGetLocationActivity extends FragmentActivity implements OnMapReadyCallback {
    static int FINE_LOCATION_PERMISSION_REQUEST_CODE = 1111;
    static int COARSE_LOCATION_PERMISSION_REQUEST_CODE = 2222;

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


        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.mapGetLocation_mapFragment);
        mapFragment.getMapAsync(this);

        confirmBtn = findViewById(R.id.mapGetLocation_confirmBtn);

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
