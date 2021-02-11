package com.example.ejlocation;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.Map;

public class MainActivity extends AppCompatActivity implements LocationListener, OnMapReadyCallback {
    private Location localizacion;
    private LocationManager location_manager;

    private TextView tv_longitud;
    private TextView tv_latitud;
    private TextView tv_altitud;
    private TextView tv_precision;

    private static final String MAP_VIEW_BUNDLE_KEY = "MapViewBundleKey";
    private MapView vistaMapa;
    private GoogleMap gmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Bundle mapViewBundle = null;
        if (savedInstanceState != null) {
            mapViewBundle = savedInstanceState.getBundle(MAP_VIEW_BUNDLE_KEY);
        }

        //Pide permisos al usuario de acceso al gps
        ActivityCompat.requestPermissions(MainActivity.this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},1);
        tv_longitud = (TextView) findViewById(R.id.str_longitud);
        tv_latitud = (TextView) findViewById(R.id.str_latitud);
        tv_altitud = (TextView) findViewById(R.id.str_altitud);
        tv_precision = (TextView) findViewById(R.id.stra_precision);

        getLocation();

        vistaMapa = findViewById(R.id.map);
        vistaMapa.onCreate(mapViewBundle);
        vistaMapa.getMapAsync(this);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        Bundle mapViewBundle = outState.getBundle(MAP_VIEW_BUNDLE_KEY);
        if (mapViewBundle == null) {
            mapViewBundle = new Bundle();
            outState.putBundle(MAP_VIEW_BUNDLE_KEY, mapViewBundle);
        }
        vistaMapa.onSaveInstanceState(mapViewBundle);
    }
    @Override
    protected void onResume() {
        super.onResume();
        vistaMapa.onResume();
    }

    @Override
    protected void onStart() {
        super.onStart();
        vistaMapa.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
        vistaMapa.onStop();
    }
    @Override
    protected void onPause() {
        vistaMapa.onPause();
        super.onPause();
    }
    @Override
    protected void onDestroy() {
        vistaMapa.onDestroy();
        super.onDestroy();
    }
    @Override
    public void onLowMemory() {
        super.onLowMemory();
        vistaMapa.onLowMemory();
    }
    @Override
    public void onMapReady(GoogleMap googleMap) {
        gmap = googleMap;
        gmap.setMinZoomPreference(12);
        LatLng ny = new LatLng(40.7143528, -74.0059731);
        gmap.moveCamera(CameraUpdateFactory.newLatLng(ny));
        googleMap.addMarker(new MarkerOptions().position(new LatLng(0,0)).title("Marker"));

        gmap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                //Añade un marcador al mapa
                //&gmap.addMarker(new MarkerOptions()
                   //     .position(latLng));
                tv_longitud.setText(getResources().getString(R.string.str_longitud) + " " + latLng.longitude);
                tv_latitud.setText(getResources().getString(R.string.str_latitud) + " " + latLng.latitude);
                //gmap.clear();
            }
        });
    }

    //LOCATION
    @SuppressLint("ResourceType")
    public void getLocation(){
        location_manager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                !=
                PackageManager.PERMISSION_GRANTED){
               Toast tostada = Toast.makeText(this, "No se han dado permisos para acceder a la localización", Toast.LENGTH_SHORT);
               tostada.show();
            return;
        }
        location_manager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 3000, 5, this);
        localizacion = location_manager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        tv_longitud.setText(getResources().getString(R.string.str_longitud) + " " + String.valueOf(localizacion.getLongitude()));
        tv_latitud.setText(getResources().getString(R.string.str_latitud) + " " + String.valueOf(localizacion.getLatitude()));
        tv_altitud.setText(getResources().getString(R.string.str_altitud) + " " + String.valueOf(localizacion.getAltitude()));
        tv_precision.setText(getResources().getString(R.string.str_precision) + " " + String.valueOf(localizacion.getAccuracy()));
    }
    @SuppressLint("ResourceType")
    @Override
    public void onLocationChanged(@NonNull Location location) {
        tv_longitud.setText(getResources().getString(R.string.str_longitud) + " " + String.valueOf(localizacion.getLongitude()));
        tv_latitud.setText(getResources().getString(R.string.str_latitud) + " " + String.valueOf(localizacion.getLatitude()));
        tv_altitud.setText(getResources().getString(R.string.str_altitud) + " " + String.valueOf(localizacion.getAltitude()));
        tv_precision.setText(getResources().getString(R.string.str_precision) + " " + String.valueOf(localizacion.getAccuracy()));
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderDisabled(@NonNull String provider) {
        Toast tostada = Toast.makeText(this, "No se encuentra la señal GPS", Toast.LENGTH_SHORT);
        tostada.show();
    }

    @Override
    public void onProviderEnabled(@NonNull String provider) {

    }

}