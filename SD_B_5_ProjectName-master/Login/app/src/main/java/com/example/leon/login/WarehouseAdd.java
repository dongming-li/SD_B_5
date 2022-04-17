package com.example.leon.login;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WarehouseAdd extends OperationActivity implements View.OnClickListener {

    private EditText email; //Email Address
    private EditText street_address; //Street Address
    private EditText city; //City Name
    private EditText state; //State Name
    private EditText zip_code; //Zip Code
    private EditText phone; //Phone Number
    private Button buttonSubmit;  //Submit
    private ProgressBar progressBar;
    private LatLng latLng;
    private String url = "http://proj-309-sd-b-5.cs.iastate.edu/database/add_store.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_warehouse_add);
        email = (EditText) findViewById(R.id.email);
        street_address = (EditText) findViewById(R.id.street_address);
        state = (EditText) findViewById(R.id.state);
        city = (EditText) findViewById(R.id.city);
        zip_code = (EditText) findViewById(R.id.zip_code);
        phone = (EditText) findViewById(R.id.phone);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        buttonSubmit = (Button) findViewById(R.id.buttonSubmit);
        progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.INVISIBLE);

        navDrawer();
        setImage();
        //Adding listener to button
        buttonSubmit.setOnClickListener(this);

    }

    private void add() {
        if (email.getText().toString().isEmpty() || street_address.getText().toString().isEmpty() ||
                state.getText().toString().isEmpty() || city.getText().toString().isEmpty() ||
                zip_code.getText().toString().isEmpty() || phone.getText().toString().isEmpty()) {
            Toast.makeText(WarehouseAdd.this, "At least one of the fields is empty.", Toast.LENGTH_LONG).show();
        } else if (!email.getText().toString().matches("^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$")) {
            email.setError("Please enter a valid email address");
        } else {
            progressBar.setVisibility(View.VISIBLE);
            try {
                getLatLong(street_address.getText().toString() + " " + city.getText().toString()
                        + " " + state.getText().toString() + " " + zip_code.getText().toString());
            } catch (IOException e) {
                e.printStackTrace();
            }
            RequestQueue requestQueue = Volley.newRequestQueue(this);
            StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    progressBar.setVisibility(View.INVISIBLE);
                    Toast.makeText(getApplicationContext(), "Warehouse Created", Toast.LENGTH_LONG).show();
                    finish();
                    startActivity(new Intent(getApplicationContext(), OperationActivity.class));
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    progressBar.setVisibility(View.INVISIBLE);
                    Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
                }
            }) {
                @Override
                protected Map<String, String> getParams() {

                    Map<String, String> parameters = new HashMap<String, String>();
                    parameters.put("company_id", MainActivity.company_id);
                    parameters.put("location_id", MainActivity.location_id);
                    parameters.put("manager_id", "1");
                    parameters.put("email", email.getText().toString());
                    parameters.put("address", street_address.getText().toString());
                    parameters.put("state", state.getText().toString());
                    parameters.put("city", city.getText().toString());
                    parameters.put("zip_code", zip_code.getText().toString());
                    parameters.put("phone", phone.getText().toString());
                    parameters.put("lat", "" + latLng.latitude);
                    parameters.put("long", "" + latLng.longitude);
                    parameters.put ("warehouse",  "1");
                    return parameters;
                }
            };
            requestQueue.add(request);
        }
    }

    private void getLatLong(String strAddress) throws IOException {
        Geocoder geocoder = new Geocoder(this);
        List<Address> address = geocoder.getFromLocationName(strAddress, 1);
        if (address == null) {
            Toast.makeText(WarehouseAdd.this, "Error: Invalid address", Toast.LENGTH_LONG).show();
        } else {
            Address location = address.get(0);
            latLng = new LatLng(location.getLatitude(), location.getLongitude());
        }
    }

    public void onClick(View view) {
        if (view == buttonSubmit) {
            add();
        }
    }

    private void setImage() {
        ImageView imgFavorite = findViewById(R.id.info);
        imgFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displayInfo();
            }
        });
    }

    private void displayInfo() {
        AlertDialog.Builder adb = new AlertDialog.Builder(WarehouseAdd.this, R.style.MyAlertDialogStyle);
        adb.setTitle("Create a warehouse information");
        adb.setMessage("This is where you can create a warehouse for your company which will act as a supplier for your stores. Fill " +
                "out the important details about your store.");
        adb.setNegativeButton("OK", null);
        adb.show();
    }
}
