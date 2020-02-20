package com.example.easyhelicopter;

import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.transition.Fade;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.easyhelicopter.BD.DataBase;
import com.example.easyhelicopter.Models.Ciudad;
import com.example.easyhelicopter.Models.Puerto;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

public class MapaActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    public Ciudad ciudad_origen = null;
    public Puerto puerto_origen = null;

    public Ciudad ciudad_destino = null;
    public Puerto puerto_destino = null;

    private TextView label_ciudad_origen;
    private TextView label_puerto_origen;
    private TextView label_ciudad_destino;
    private TextView label_puerto_destino;


    private Button btn_ciudad_origen;
    private Button btn_puerto_origen;
    private Button btn_ciudad_destino;
    private Button btn_puerto_destino;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_mapa);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        label_ciudad_origen = (TextView) findViewById(R.id.label_ciudad_origen);
        label_puerto_origen = (TextView) findViewById(R.id.label_puerto_origen);
        label_ciudad_destino = (TextView) findViewById(R.id.label_ciudad_destino);
        label_puerto_destino = (TextView) findViewById(R.id.label_puerto_destino);

        btn_ciudad_origen = (Button) findViewById(R.id.btn_ciudad_origen);
        btn_puerto_origen = (Button) findViewById(R.id.btn_puerto_origen);
        btn_ciudad_destino = (Button) findViewById(R.id.btn_ciudad_destino);
        btn_puerto_destino = (Button) findViewById(R.id.btn_puerto_destino);


        btn_ciudad_origen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listar_ciudades_origen();
            }
        });

        btn_puerto_origen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listar_puertos_origen();
            }
        });
        btn_ciudad_destino.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listar_ciudades_destino();
            }
        });
        btn_puerto_destino.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listar_puertos_destino();
            }
        });

    }

    public void marcar_en_mapa(double ltd, double lon, String name){
        LatLng posicion_actual = new LatLng(ltd,lon);
        mMap.addMarker(new MarkerOptions().position(posicion_actual).title(name));
       // mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(posicion_actual,15));

        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(posicion_actual)
                .zoom(16)
                .bearing(360)
                .build();
        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
    }

    public void listar_ciudades_origen(){
        String[] lista = new String[DataBase.BD_Ciudades.size()];
        int cont = 0;
        for(Ciudad ciudad : DataBase.BD_Ciudades){
                lista[cont] = ciudad.getNombre();
                cont++;
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Seleccione la ciudad de origen")
                .setItems(lista, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        ciudad_origen = DataBase.BD_Ciudades.get(which);
                        label_ciudad_origen.setText(ciudad_origen.getNombre());
                        puerto_origen = null;
                        label_puerto_origen.setText("Puerto de origen");
                        validar_botones_visibles();
                    }
                });
        builder.create();
        builder.show();
    }
    public void listar_ciudades_destino() {

        String[] lista = new String[DataBase.BD_Ciudades.size()];
        int cont = 0;
        for (Ciudad ciudad : DataBase.BD_Ciudades) {
                lista[cont] = ciudad.getNombre();
                cont++;
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Seleccione la ciudad de destino")
                .setItems(lista, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        ciudad_destino = DataBase.BD_Ciudades.get(which);
                        label_ciudad_destino.setText(ciudad_destino.getNombre());
                        puerto_destino = null;
                        label_puerto_destino.setText("Puerto de destino");
                    }
                });
        builder.create();
        builder.show();
    }

    public void listar_puertos_origen() {
        if (ciudad_origen==null){
            TableLayout tabla = findViewById(R.id.tabla);
            Snackbar.make(tabla, "Debe seleccionar una ciudad de origen", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
        }else{
            String[] lista = new String[ciudad_origen.getPuertos().size()];
            int cont = 0;
            for (Puerto puerto : ciudad_origen.getPuertos()) {
                lista[cont] = puerto.getNombre();
                cont++;
            }
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Seleccione el puerto de origen")
                    .setItems(lista, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            puerto_origen = ciudad_origen.getPuertos().get(which);
                            String name_puerto = puerto_origen.getNombre();
                            if (puerto_origen.getNombre().length()>28){
                                name_puerto = name_puerto.substring(0,24)+"...";
                            }
                            label_puerto_origen.setText(name_puerto);
                            mMap.clear();
                            marcar_en_mapa(puerto_origen.getLatitud(),puerto_origen.getLongitud(), puerto_origen.getNombre());
                            validar_botones_visibles();
                            validar_pintar_ruta();
                        }
                    });
            builder.create();
            builder.show();
        }

    }

    public void listar_puertos_destino() {
        if (ciudad_destino==null){
            TableLayout tabla = findViewById(R.id.tabla);
            Snackbar.make(tabla, "Debe seleccionar una ciudad de destino", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
        }else{
            String[] lista = new String[ciudad_destino.getPuertos().size()];
            int cont = 0;
            for (Puerto puerto : ciudad_destino.getPuertos()) {
                lista[cont] = puerto.getNombre();
                cont++;
            }
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Seleccione el puerto de destino")
                    .setItems(lista, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            puerto_destino = ciudad_destino.getPuertos().get(which);
                            String name_puerto = puerto_destino.getNombre();
                            if (puerto_destino.getNombre().length()>28){
                                name_puerto = name_puerto.substring(0,24)+"...";
                            }

                            label_puerto_destino.setText(name_puerto);

                            validar_pintar_ruta();
                        }
                    });
            builder.create();
            builder.show();
        }

    }

    public void validar_botones_visibles(){
        TableRow fila_botones_destino, fila_labels_destino;
        fila_botones_destino = (TableRow) findViewById(R.id.fila_botones_destino);
        fila_labels_destino = (TableRow) findViewById(R.id.fila_labels_destino);
        if (ciudad_origen !=null && puerto_origen != null){
            fila_botones_destino.setVisibility(View.VISIBLE);
            fila_labels_destino.setVisibility(View.VISIBLE);
        }else{
            if (ciudad_destino != null){
                fila_botones_destino.setVisibility(View.VISIBLE);
                fila_labels_destino.setVisibility(View.VISIBLE);
            }else{
                fila_botones_destino.setVisibility(View.GONE);
                fila_labels_destino.setVisibility(View.GONE);
            }
        }
    }

    public void validar_pintar_ruta(){
        if (puerto_origen !=null && puerto_destino != null){
            mMap.clear();
            mMap.addMarker(new MarkerOptions().position(new LatLng(puerto_origen.getLatitud(), puerto_origen.getLongitud())).title(puerto_origen.getNombre()));
            mMap.addMarker(new MarkerOptions().position(new LatLng(puerto_destino.getLatitud(), puerto_destino.getLongitud())).title(puerto_destino.getNombre()));
            float zoom = 5;
            if (ciudad_destino.equals(ciudad_origen)) zoom = 11;

            Location locacion_punto_origen = new Location(puerto_origen.getNombre());
            locacion_punto_origen.setLatitude(puerto_origen.getLatitud());
            locacion_punto_origen.setLongitude(puerto_origen.getLongitud());

            Location locacion_punto_destino = new Location(puerto_destino.getNombre());
            locacion_punto_destino.setLatitude(puerto_destino.getLatitud());
            locacion_punto_destino.setLongitude(puerto_destino.getLongitud());

            float distance = locacion_punto_origen.distanceTo(locacion_punto_destino);
            Toast.makeText(this, "Distancia: "+(distance/1000)+"Km", Toast.LENGTH_LONG).show();
            CameraPosition cameraPosition = new CameraPosition.Builder()
                    .target(new LatLng(puerto_origen.getLatitud(), puerto_origen.getLongitud()))
                    .zoom(zoom)
                    .bearing(360)
                    .build();
            mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

            Polyline line = mMap.addPolyline(new PolylineOptions()
                    .add(new LatLng(puerto_origen.getLatitud(), puerto_origen.getLongitud()), new LatLng(puerto_destino.getLatitud(), puerto_destino.getLongitud()))
                    .width(5)
                    .color(Color.RED));

        }
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
        UiSettings uiSettings = mMap.getUiSettings();
        uiSettings.setZoomControlsEnabled(true);

        //estilo del mapa
        try {

            boolean success = googleMap.setMapStyle(
                    MapStyleOptions.loadRawResourceStyle(
                            this, R.raw.mapstyle));

            if (!success) {
                Log.e("MapActivity", "Style parsing failed.");
            }
        } catch (Resources.NotFoundException e) {
            Log.e("MapActivity", "Can't find style. Error: ", e);
        }


        int permissionCheck = ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);

        if (permissionCheck == PackageManager.PERMISSION_DENIED){
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,Manifest.permission.ACCESS_FINE_LOCATION)){
            }else {
                ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},1);
            }
        }
        permissionCheck = ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);
        //Add a marker in Sydney and move the camera
        Location my_posicion = getMyPosition();
        double lat;
        double lon;
        if (my_posicion==null){
            //colocamos la localizacion de colombia si no lee la actual
            lat = 2.8894434;
            lon = -73.783892;
            //Snackbar.make(getCurrentFocus(), "Por favor asegurese de tener el GPS encendido es necesario para el uso de la aplicacion", Snackbar.LENGTH_LONG).setAction("Action", null).show();
            Toast.makeText(this, "Por favor asegurese de tener el GPS encendido es necesario para el uso de la aplicacion", Toast.LENGTH_LONG).show();
        }else{
            lat = my_posicion.getLatitude();
            lon = my_posicion.getLongitude();
        }


        LatLng posicion_actual = new LatLng(lat,lon);
        mMap.addMarker(new MarkerOptions().position(posicion_actual).title("Posicion actual"));

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(posicion_actual,15));
        mMap.setMyLocationEnabled(true);


    }

    public Location getMyPosition(){
        LocationManager locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);

        LocationListener locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {

            }
        };
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return null;
        }
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
        Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        return  location;
    }
}


