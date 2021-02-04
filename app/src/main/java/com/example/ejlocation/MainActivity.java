package com.example.ejlocation;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {
    private Location localizacion;
    private LocationManager localizador;            // TODO: 04/02/2021 Hay que instanciarlos
    private boolean hay_conexion_gps;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Pide permisos al usuario de acceso al gps
        ActivityCompat.requestPermissions(MainActivity.this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},1);

        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                !=
                PackageManager.PERMISSION_GRANTED){
                    //Estos son elementos del activity, hay que definirlos aun
                    //latitud.setText("No se han definido los permisos necesarios");
                    //longitud.setText("");
                    return;
                }

        // TODO: 04/02/2021 Peta por usar un metodo de un objeto nulo (localizador, que esta sin instanciar)
        //true/false si el gps esta activado
        hay_conexion_gps = localizador.isProviderEnabled(LocationManager.GPS_PROVIDER);



    }
}