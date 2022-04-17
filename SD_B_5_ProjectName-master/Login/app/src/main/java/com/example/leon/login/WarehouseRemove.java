package com.example.leon.login;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

public class WarehouseRemove extends OperationActivity implements View.OnClickListener {


    private Button buttonSubmit;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_remove);
        buttonSubmit = (Button) findViewById(R.id.buttonSubmit);
        progressBar = findViewById(R.id.progressBar);
        buttonSubmit.setOnClickListener(this);
        progressBar.setVisibility(View.INVISIBLE);
        navDrawer();
        setImage();
    }

    private void removeStore() {
        progressBar.setVisibility(View.VISIBLE);
        String url_search = "http://proj-309-sd-b-5.cs.iastate.edu/database/remove_store.php?" +
                "company_id=" + MainActivity.company_id + "&location_id=" + MainActivity.location_id;

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url_search, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressBar.setVisibility(View.INVISIBLE);
                Toast.makeText(getApplicationContext(), "Warehouse Removed", Toast.LENGTH_LONG).show();
                finish();
                startActivity(new Intent(getApplicationContext(), OperationActivity.class));
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
        AlertDialog.Builder adb = new AlertDialog.Builder(WarehouseRemove.this, R.style.MyAlertDialogStyle);
        adb.setTitle("Remove Warehouse Information");
        adb.setMessage("This is where you can remove the warehouse from the company. Just click on the button and it will remove the " +
                "warehouse you are located in.");
        adb.setNegativeButton("OK", null);
        adb.show();
    }

    @Override
    public void onClick(View v) {
        if (v == buttonSubmit) {
            removeStore();
        }
    }
}
