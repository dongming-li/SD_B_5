package com.example.leon.login;

import android.app.Activity;
import android.app.DatePickerDialog;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.json.JSONArray;

import static com.example.leon.login.R.id.buttonScan;
import static com.example.leon.login.R.id.itemListing;
import static com.example.leon.login.R.id.start;


public class OrderRequestActivity extends OperationActivity implements View.OnClickListener {

    private EditText supplier;
    private TextView date;
    private Button buttonSupplier;
    private Button buttonDate;
    private Button buttonSubmit;
    private ProgressBar progressBar;
    private String information;
    private List<String> details;
    String temp="";
    private String url = "http://proj-309-sd-b-5.cs.iastate.edu/database/add_order.php";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_request);
        supplier =  findViewById(R.id.supplier);
        date = (TextView) findViewById(R.id.date);
        buttonSupplier = (Button) findViewById(R.id.buttonSupplier);
        buttonDate = (Button) findViewById(R.id.buttonDate);
        buttonSubmit = (Button) findViewById(R.id.buttonSubmit);
        buttonSupplier.setOnClickListener(this);
        navDrawer();
        buttonDate.setOnClickListener(this);
        buttonSubmit.setOnClickListener(this);
        progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.INVISIBLE);
        setImage();
        //supplier.setText(StoreActivity.supplier.substring(StoreActivity.supplier.indexOf("=")+1));

        /**
         if (MainActivity.company_id == null) {
         MainActivity.company_id = CompanyRegistration.company_id;
         }
         if (MainActivity.location_id == null) {
         MainActivity.location_id = "1";
         }
         */
    }

    @Override
    public void onClick(View v) {

        if (v == buttonSupplier) {
            verifySupplier();
        } else if (v == buttonDate) {
            // Get Current Date
            final Calendar c = Calendar.getInstance();
            //year, month and date
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            //Create a new date picker
            DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                    new DatePickerDialog.OnDateSetListener() {

                        @Override
                        public void onDateSet(DatePicker view, int year,
                                              int month, int day) {

                            date.setText(year + "-" + (month + 1) + "-" + day);

                        }
                    }, year, month, day);
            //Min date can be today.
            datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
            //Open the date picker widget.
            datePickerDialog.show();
        } else if (v == buttonSubmit) {
            listToString();
            submitOrder();
        }
    }

    private void verifySupplier () {
        if (supplier.getText().toString().isEmpty()) {
            supplier.setError("Enter Location ID");
        } else {
            String url_search = "http://proj-309-sd-b-5.cs.iastate.edu/database/warehouse.php?"
                    + "company_id=" + MainActivity.company_id;
            StringRequest stringRequest = new StringRequest(Request.Method.GET, url_search, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {

                    information = response;
                    details = new ArrayList<String>(Arrays.asList(information.split(",")));

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
    }

    private void submitOrder() {
        verifySupplier();
        if (supplier.getText().toString().isEmpty()) {
            supplier.setError("Enter location ID");
        }
        else if (date.getText().toString().isEmpty()) {
            Toast.makeText(OrderRequestActivity.this, "Enter the delivery date", Toast.LENGTH_LONG).show();
        }
        else if (!details.contains(supplier.getText().toString())) {
            supplier.setError("Warehouse does not exist");
        }
        else {
            progressBar.setVisibility(View.VISIBLE);
            RequestQueue requestQueue = Volley.newRequestQueue(this);
            StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    progressBar.setVisibility(View.INVISIBLE);
                    Toast.makeText(getApplicationContext(), "Order Submitted", Toast.LENGTH_LONG).show();
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
                    parameters.put("destination_id", supplier.getText().toString());
                    parameters.put("item_list", temp);
                    parameters.put("date", date.getText().toString());
                    parameters.put("count", OrderRequestAdd.itemList.size() + "");

                    return parameters;
                }
            };
            requestQueue.add(request);
        }
    }

    private void listToString() {
        for (int i=0; i<OrderRequestAdd.itemList.size();i++) {
            temp +=  OrderRequestAdd.itemList.get(i) + ",";
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
        AlertDialog.Builder adb = new AlertDialog.Builder(OrderRequestActivity.this, R.style.MyAlertDialogStyle);
        adb.setTitle("Submit Order Information");
        adb.setMessage("This is where you submit the order to the warehouse. Your order includes date, supplier, and the list of items " +
                "requested from your supplier.");
        adb.setNegativeButton("OK", null);
        adb.show();
    }
}