package com.example.ejlocation;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
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
    private LocationManager localizador;
    private LocationManager location_manager;
    private boolean hay_conexion_gps;

    private TextView tv;

    double latitud = 0;
    double longitud = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Pide permisos al usuario de acceso al gps
        ActivityCompat.requestPermissions(MainActivity.this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},1);



        getLocation();
        tv = (TextView) findViewById(R.id.tv);
        tv.setText("latidud: "+ latitud +"  longitud: " + longitud);
    }
    public void getLocation(){
        location_manager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                !=
                PackageManager.PERMISSION_GRANTED){
                tv.setText("No se han dado permisos para acceder a la localización");
            return;
        }
        location_manager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 3000, 5, this);
        //ESTO VA
        localizacion = location_manager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        latitud = localizacion.getLatitude();
        longitud = localizacion.getLongitude();
    }
    @Override
    public void onLocationChanged(@NonNull Location location) {
        latitud = location.getLatitude();
        longitud = location.getLongitude();
        tv.setText("latidud: "+ latitud +"  longitud: " + longitud);
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderDisabled(@NonNull String provider) {
        tv.setText("No se encuentra señal GPS");
    }

    @Override
    public void onProviderEnabled(@NonNull String provider) {

    }




       /* //Asignamos el manejador de Location Service a la variable localizador
        localizador = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        //true/false si el gps esta activado
        hay_conexion_gps = localizador.isProviderEnabled(LocationManager.GPS_PROVIDER);*/



        //Asigna a localizador la ultima posicion que se obtubo a traves del GPS
        /*localizacion = localizador.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        latitud = localizacion.getLatitude();
        longitud = localizacion.getLongitude();*/



}