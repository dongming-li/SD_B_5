package com.example.leon.login;

import android.app.ProgressDialog;
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

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DirectoryUpdate extends OperationActivity {

    private EditText firstName;
    private EditText lastName;
    private EditText phoneNumber;
    private EditText streetAddress;
    private EditText city;
    private EditText state;
    private EditText zip_code;
    private ProgressBar progressBar;
    private LatLng latLng;
    private Button buttonSubmit;
    private String url = "http://proj-309-sd-b-5.cs.iastate.edu/database/update_directory.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_directory_update);

        firstName = (EditText) findViewById(R.id.firstName);
        lastName = (EditText) findViewById(R.id.lastName);
        phoneNumber = (EditText) findViewById(R.id.phoneNumber);
        streetAddress = (EditText) findViewById(R.id.streetAddress);
        city = (EditText) findViewById(R.id.city);
        state = (EditText) findViewById(R.id.state);
        zip_code = (EditText) findViewById(R.id.zip_code);
        navDrawer();
        progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.INVISIBLE);
        buttonSubmit = (Button) findViewById(R.id.buttonSubmit);
        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                update();
            }
        });
        setImage();
    }

    private void update() {
        if (firstName.getText().toString().isEmpty() && lastName.getText().toString().isEmpty()
                && phoneNumber.getText().toString().isEmpty() && streetAddress.getText().toString().isEmpty() &&
                city.getText().toString().isEmpty() && state.getText().toString().isEmpty() && zip_code.getText().toString().isEmpty()) {
            Toast.makeText(DirectoryUpdate.this, "Enter at least one field.", Toast.LENGTH_LONG).show();
        } else {
            progressBar.setVisibility(View.VISIBLE);
            if (!streetAddress.getText().toString().isEmpty()) {
                try {
                    getLatLong(streetAddress.getText().toString() + " " + city.getText().toString()
                            + " " + state.getText().toString() + " " + zip_code.getText().toString());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            RequestQueue requestQueue = Volley.newRequestQueue(this);
            StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    progressBar.setVisibility(View.INVISIBLE);
                    Toast.makeText(getApplicationContext(), "User updated", Toast.LENGTH_LONG).show();
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
                    parameters.put("user_id", DirectoryAdd.userID);
                    parameters.put("first_name", firstName.getText().toString());
                    parameters.put("last_name", lastName.getText().toString());
                    parameters.put("phone_number", phoneNumber.getText().toString());
                    parameters.put("address", streetAddress.getText().toString());
                    parameters.put("city", city.getText().toString());
                    parameters.put("state", state.getText().toString());
                    parameters.put("zip_code", zip_code.getText().toString());
                    if (!streetAddress.getText().toString().isEmpty()) {
                        parameters.put("lat", "" + latLng.latitude);
                        parameters.put("long", "" + latLng.longitude);
                    }
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
            Toast.makeText(DirectoryUpdate.this, "Error: Invalid address", Toast.LENGTH_LONG).show();
        } else {
            Address location = address.get(0);
            latLng = new LatLng(location.getLatitude(), location.getLongitude());
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
        AlertDialog.Builder adb = new AlertDialog.Builder(DirectoryUpdate.this, R.style.MyAlertDialogStyle);
        adb.setTitle("Updating user information in company directory");
        adb.setMessage("This is where you fill out the information that you want to change about yourself in the company directory.");
        adb.setNegativeButton("OK", null);
        adb.show();
    }
}

