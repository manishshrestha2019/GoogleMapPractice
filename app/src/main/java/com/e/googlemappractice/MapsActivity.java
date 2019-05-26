package com.e.googlemappractice;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.ArrayList;
import java.util.List;

import model.LatitudeLongitude;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private FusedLocationProviderClient fusedLocationProviderClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
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

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);


        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        fusedLocationProviderClient.getLastLocation()
                .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        if (location != null) {

                            new LatitudeLongitude(location.getLatitude(),location.getLongitude(),"");
                            CameraUpdate center,zoom;
                                for (int i=0; i<location.size();i++){
                                    center=CameraUpdateFactory.newLatLng(new LatLng(latlngs.get(i).getLat(),
                                            latlngs.get(i).getLon()));

                                    zoom=CameraUpdateFactory.zoomTo(16);
                                    mMap.addMarker(new MarkerOptions().position(
                                            new LatLng(latlngs.get(i).getLat(),latlngs.get(i).getLon())).title(latlngs.get(i).getMarker()));

                                    mMap.moveCamera(center);
                                    mMap.moveCamera(zoom);
                                    mMap.getUiSettings().setZoomControlsEnabled(true);

                        }
                    }
                });

//        List<LatitudeLongitude> latlngs=new ArrayList<>();
//
//        latlngs.add(new LatitudeLongitude(27.7060033,85.3296575,"Softwarica College of IT and E-Commerce, Block E"));
//        latlngs.add(new LatitudeLongitude(27.705502,85.329994,"Softwarica Canteen"));
//
//        CameraUpdate center,zoom;
//        for (int i=0; i<latlngs.size();i++){
//            center=CameraUpdateFactory.newLatLng(new LatLng(latlngs.get(i).getLat(),
//                    latlngs.get(i).getLon()));
//
//            zoom=CameraUpdateFactory.zoomTo(16);
//            mMap.addMarker(new MarkerOptions().position(
//                    new LatLng(latlngs.get(i).getLat(),latlngs.get(i).getLon())).title(latlngs.get(i).getMarker()));
//
//            mMap.moveCamera(center);
//            mMap.moveCamera(zoom);
//            mMap.getUiSettings().setZoomControlsEnabled(true);
//
//        }
    }

}
