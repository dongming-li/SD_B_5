package com.example.leon.login;

import android.location.Address;
import android.location.Geocoder;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.auth.FirebaseAuth;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class StoreActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private float zoom = 10;
    private String information;
    private List <String> details = new ArrayList<>();
    private Marker myMarker;
    static String supplier;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store);
        //address = findViewById(R.id.location);
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

        retrieveInfo();
        addMarker();
        //populateMap();

    }

    private void addMarker(){
        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        CameraUpdate update = CameraUpdateFactory.newLatLngZoom(sydney, zoom);
        mMap.moveCamera(update);
    }

    private void retrieveInfo(){
        String url_search = "http://proj-309-sd-b-5.cs.iastate.edu/database/warehouse.php?"
                + "company_id=" + MainActivity.company_id;
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url_search, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                information = response;
                test();

/**
                mMap.addMarker(new MarkerOptions()
                        .position(temp)
                        .title("Warehouse" + "1")
                        .snippet("Location ID=" + destination_id));
                mMap.moveCamera(CameraUpdateFactory.newLatLng(temp));
 */

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }

    private void test() {

        information.substring(0,information.indexOf(':'));

        information.substring(information.indexOf(':'+ 1 ), information.indexOf('|'));

        String destination_id = information.substring(information.indexOf('|'+1), information.indexOf(','));

        //Toast.makeText(getApplicationContext(), destination_id, Toast.LENGTH_LONG).show();

    }
    private void print() {
        //String = 122:133|2, Lat:Long-destination





    }

    private void populateMap(){
        //String = 122:133|2, Lat:Long-destination
        LatLng temp = new LatLng(Double.parseDouble(information.substring(0,information.indexOf(':')))
                ,Double.parseDouble(information.substring(information.indexOf(':'+ 1 ), information.indexOf('|'))));

        String destination_id = information.substring(information.indexOf('|'+1), information.indexOf(','));

       // mMap.addMarker(new MarkerOptions().position(temp).title("Marker in Sydney"));

        myMarker = mMap.addMarker(new MarkerOptions()
                .position(temp)
                .title("Warehouse" + "1")
                .snippet("Location ID=" + destination_id));


        mMap.moveCamera(CameraUpdateFactory.newLatLng(temp));
        CameraUpdate update = CameraUpdateFactory.newLatLngZoom(temp, zoom);
        mMap.moveCamera(update);
        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                if (marker.equals(myMarker)) {
                    supplier = myMarker.getSnippet();
                    Toast.makeText(getApplicationContext(), supplier, Toast.LENGTH_LONG).show();
                    return true;
                } else return false;
            }
        });}


    /**
     details = new ArrayList<String>(Arrays.asList(information.split(",")));
     for (int i=0; i<details.size(); i++) {
     LatLng temp = new LatLng(Double.parseDouble(details.get(i)
     .substring(0, details.get(i).indexOf(':'))),
     Double.parseDouble(details.get(i)
     .substring(details.get(i).indexOf(':')+1, details.get(i).indexOf('|'))));

     String destination_id = details.get(i)
     .substring(details.get(i).indexOf('|')+1, details.get(i).indexOf(','));

     myMarker = mMap.addMarker(new MarkerOptions()
     .position(temp)
     .title("Warehouse" + i+1)
     .snippet("Location ID=" + destination_id)
     .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));

     mMap.moveCamera(CameraUpdateFactory.newLatLng(temp));

     mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
    @Override
    public boolean onMarkerClick(Marker marker) {
    if (marker.equals(myMarker)) {
    supplier = myMarker.getSnippet();
    Toast.makeText(getApplicationContext(), supplier, Toast.LENGTH_LONG).show();
    return true;
    }
    else return false;
    }
    });
     }
     */
}
