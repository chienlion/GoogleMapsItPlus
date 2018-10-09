package com.example.anonymous.googlemapsitplus;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private FusedLocationProviderClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        client = LocationServices.getFusedLocationProviderClient(this);
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }

    public void zommIn(View view) {
        mMap.animateCamera(CameraUpdateFactory.zoomIn());
    }

    public void zommOut(View view) {
        mMap.animateCamera(CameraUpdateFactory.zoomOut());
    }

    public void goToHaNoi(View view) {
        LatLng hanoi = new LatLng(21.686868,105.161616);
        CameraPosition pos  = new CameraPosition(hanoi,16,0,0);
        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(pos));

        MarkerOptions options = new MarkerOptions().position(hanoi).title("Ha noi").snippet("thu do viet nam");
        mMap.addMarker(options);
    }

    public void vetinh(View view) {
        mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
    }
    private void requestPermission(String permission, int requestCode){
        if(ActivityCompat.checkSelfPermission(this,permission)!= PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,new String[]{permission},requestCode);
            
        }else{
            //TODO lay thong tin vi tri
            client.getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
                @Override
                public void onSuccess(Location location) {
                    if(location!=null){
                        LatLng here = new LatLng(location.getLatitude(),location.getLongitude());
                        MarkerOptions options = new MarkerOptions().position(here);
                        mMap.addMarker(options);
                        CameraPosition pos = new CameraPosition(here,15,0,0);
                        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(pos));
                    }
                }
            });
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(ActivityCompat.checkSelfPermission(this,permissions[0])==PackageManager.PERMISSION_GRANTED){
            //TODO lay thong tin vi tri
            client.getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
                @Override
                public void onSuccess(Location location) {
                    if(location!=null){
                        LatLng here = new LatLng(location.getLatitude(),location.getLongitude());
                        MarkerOptions options = new MarkerOptions().position(here);
                        mMap.addMarker(options);
                        CameraPosition pos = new CameraPosition(here,15,0,0);
                        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(pos));
                    }
                }
            });

        }else{
            Toast.makeText(this, "Khong cho phep", Toast.LENGTH_SHORT).show();
        }
    }

    public void myLocation(View view) {
        requestPermission(Manifest.permission.ACCESS_COARSE_LOCATION,1);
    }
}
