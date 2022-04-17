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
import com.firebase.client.core.operation.Operation;

public class CheckActivity extends OperationActivity implements View.OnClickListener {

    private String url = "http://proj-309-sd-b-5.cs.iastate.edu/database/search.php?company_id=";
    private Button buttonCheck;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check);
        progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.INVISIBLE);
        buttonCheck = (Button) findViewById(R.id.buttonCheck);
        buttonCheck.setOnClickListener(this);
        navDrawer();
        setImage();
    }

    private void check() {
        progressBar.setVisibility(View.VISIBLE);
        String url_search = url + MainActivity.company_id + "&" + "upc=" + ScanActivity.barcode;
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url_search, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response.equals("0 results")) {
                    progressBar.setVisibility(View.INVISIBLE);
                    Toast.makeText(getApplicationContext(), "No item listing found in the catalog", Toast.LENGTH_LONG).show();
                    finish();
                    startActivity(new Intent(getApplicationContext(), AddActivity.class));
                } else {
                    progressBar.setVisibility(View.INVISIBLE);
                    Toast.makeText(getApplicationContext(), "Item found in the catalog", Toast.LENGTH_LONG).show();
                    finish();
                    startActivity(new Intent(getApplicationContext(), AddQuantityActivity.class));
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressBar.setVisibility(View.INVISIBLE);
                Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    @Override
    public void onClick(View v) {
        if (v == buttonCheck) {
            check();
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
        AlertDialog.Builder adb = new AlertDialog.Builder(CheckActivity.this, R.style.MyAlertDialogStyle);
        adb.setTitle("Product Check Information");
        adb.setMessage("This is where you can check if the scanned product exists in the system already or not.");
        adb.setNegativeButton("OK", null);
        adb.show();
    }
}
