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

public class AddQuantityActivity extends OperationActivity implements View.OnClickListener {

    private EditText quantity;
    private Button buttonSubmit;
    private TextView itemListing;
    private ProgressBar progressBar;
    private String url = "http://proj-309-sd-b-5.cs.iastate.edu/database/add.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_quantity);
        quantity = (EditText) findViewById(R.id.quantity);
        progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.INVISIBLE);
        buttonSubmit = (Button) findViewById(R.id.buttonSubmit);
        buttonSubmit.setOnClickListener(this);
        itemListing = (TextView) findViewById(R.id.itemListing);
        itemListing.setOnClickListener(this);
        navDrawer();
        setImage();
    }

    private void addQuantity() {
        if (quantity.getText().toString().isEmpty())
            Toast.makeText(AddQuantityActivity.this, "Please enter the quantity.", Toast.LENGTH_LONG).show();
        else {
            progressBar.setVisibility(View.VISIBLE);
            RequestQueue requestQueue = Volley.newRequestQueue(this);
            StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    progressBar.setVisibility(View.INVISIBLE);
                    Toast.makeText(getApplicationContext(), "Item quantity added", Toast.LENGTH_LONG).show();
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
                    parameters.put("upc", ScanActivity.barcode);
                    parameters.put("quantity", quantity.getText().toString());
                    return parameters;
                }
            };
            requestQueue.add(request);
        }
    }

    @Override
    public void onClick(View v) {
        if (v == buttonSubmit) {
            addQuantity();
        }
        else if (v == itemListing) {
            finish();
            startActivity(new Intent(this, UpdateActivity.class));
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
        AlertDialog.Builder adb = new AlertDialog.Builder(AddQuantityActivity.this, R.style.MyAlertDialogStyle);
        adb.setTitle("Increase Product Quantity Information");
        adb.setMessage("This is where you can increase the quantity of the product in the inventory");
        adb.setNegativeButton("OK", null);
        adb.show();
    }
}
