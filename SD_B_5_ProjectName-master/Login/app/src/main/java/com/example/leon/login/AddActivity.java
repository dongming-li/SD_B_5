package com.example.leon.login;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;


public class AddActivity extends OperationActivity {

    private TextView upc;
    private EditText name; //Name
    private EditText quantity; //Quantity
    private EditText description; //Description
    private EditText bin_id;  //Item location in the store
    private EditText manufacturer_price; //Manufacturer price
    private EditText msrp; // Selling Price
    private Button buttonSubmit;  //Submit
    private ProgressBar progressBar; //progress Bar
    private String url = "http://proj-309-sd-b-5.cs.iastate.edu/database/add.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.INVISIBLE);
        upc = (TextView) findViewById(R.id.UPC);
        name = (EditText) findViewById(R.id.Name);
        quantity = (EditText) findViewById(R.id.Quantity);
        description = (EditText) findViewById(R.id.Description);
        bin_id = (EditText) findViewById(R.id.bin_id);
        manufacturer_price = (EditText) findViewById(R.id.ManufacturerPrice);
        msrp = (EditText) findViewById(R.id.Msrp);
        buttonSubmit = (Button) findViewById(R.id.buttonSubmit);
        navDrawer();
        setImage();
        //Get the UPC code that scanning in operation activity class returns.

        /**
        if (MainActivity.company_id == null) {
            MainActivity.company_id = CompanyRegistration.company_id;
        }
        if (MainActivity.location_id == null) {
            MainActivity.location_id = "1";
        }
         */
        String temp = "UPC: " + ScanActivity.barcode;
        upc.setText(temp);
        //Adding listener to button
        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                add();
            }
        });
    }

    private void add() {
        if (name.getText().toString().isEmpty() || quantity.getText().toString().isEmpty() ||
                description.getText().toString().isEmpty() || bin_id.getText().toString().isEmpty() ||
                manufacturer_price.getText().toString().isEmpty() || msrp.getText().toString().isEmpty()) {
            Toast.makeText(AddActivity.this, "At least one of the fields is empty.", Toast.LENGTH_LONG).show();
        } else {
            progressBar.setVisibility(View.VISIBLE);
            RequestQueue requestQueue = Volley.newRequestQueue(this);
            StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    progressBar.setVisibility(View.INVISIBLE);
                    Toast.makeText(getApplicationContext(), "Item added", Toast.LENGTH_LONG).show();
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
                    parameters.put ("company_id", MainActivity.company_id);
                    parameters.put ("location_id", MainActivity.location_id);
                    parameters.put("upc", ScanActivity.barcode);
                    parameters.put("name", name.getText().toString());
                    parameters.put("quantity", quantity.getText().toString());
                    parameters.put("description", description.getText().toString());
                    parameters.put("bin_id", bin_id.getText().toString());
                    parameters.put("manufacturer_price", manufacturer_price.getText().toString());
                    parameters.put("msrp", msrp.getText().toString());

                    return parameters;
                }
            };
            requestQueue.add(request);
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
        AlertDialog.Builder adb = new AlertDialog.Builder(AddActivity.this, R.style.MyAlertDialogStyle);
        adb.setTitle("Add Product Information");
        adb.setMessage("This is where you fill out the necessary information about the product to add it the company catalogue.");
        adb.setNegativeButton("OK", null);
        adb.show();
    }
}


