package com.example.tourviet;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
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

public class MapGetLocationActivity extends FragmentActivity implements OnMapReadyCallback {
    static int FINE_LOCATION_PERMISSION_REQUEST_CODE = 1111;
    static int COARSE_LOCATION_PERMISSION_REQUEST_CODE = 2222;
    GoogleMap myMap;
    FusedLocationProviderClient fusedLocationProviderClient;
    LatLng userLocation = new LatLng(0, 0);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_get_location);

        getPerMission(Manifest.permission.ACCESS_FINE_LOCATION, FINE_LOCATION_PERMISSION_REQUEST_CODE);
        getPerMission(Manifest.permission.ACCESS_COARSE_LOCATION, COARSE_LOCATION_PERMISSION_REQUEST_CODE);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        myMap = googleMap;
        setUserLocation();
        myMap.setMyLocationEnabled(true);
        myMap.getUiSettings().setCompassEnabled(true);
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
                final Task location = fusedLocationProviderClient.getLastLocation();
                location.addOnCompleteListener(new OnCompleteListener() {
                    @Override
                    public void onComplete(@NonNull Task task) {
                        if (task.isSuccessful()) {
                            Location currentLocation = (Location) task.getResult();
                            userLocation = new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude());
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
