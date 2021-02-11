package com.example.ejlocation;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements LocationListener{
    private Location localizacion;
    private LocationManager location_manager;
    private boolean hay_conexion_gps;

    private TextView tv_longitud;
    private TextView tv_latitud;
    private TextView tv_altitud;
    private TextView tv_precision;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Pide permisos al usuario de acceso al gps
        ActivityCompat.requestPermissions(MainActivity.this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},1);
        tv_longitud = (TextView) findViewById(R.id.str_longitud);
        tv_latitud = (TextView) findViewById(R.id.str_latitud);
        tv_altitud = (TextView) findViewById(R.id.str_altitud);
        tv_precision = (TextView) findViewById(R.id.stra_precision);
        getLocation();
    }
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