package com.example.leon.login;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.auth.FirebaseAuth;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.w3c.dom.Text;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class OrderRequestAdd extends OperationActivity implements View.OnClickListener {


    private Button buttonAdd;
    private Button buttonSubmit;
    ArrayAdapter<String> adapter;
    private EditText editText;
    static ArrayList<String> itemList;
    private String Response;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_request_add);
        buttonAdd = (Button) findViewById(R.id.buttonAdd);
        buttonSubmit = findViewById(R.id.buttonSubmit);
        buttonAdd.setOnClickListener(this);
        buttonSubmit.setOnClickListener(this);
        itemList = new ArrayList<>();
        adapter = new ArrayAdapter<>(this, R.layout.layout, R.id.txtview, itemList);
        final ListView listV = (ListView) findViewById(R.id.list);
        listV.setAdapter(adapter);
        navDrawer();
        editText = (EditText) findViewById(R.id.txtInput);
        listV.setOnItemClickListener(new android.widget.AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                AlertDialog.Builder adb = new AlertDialog.Builder(OrderRequestAdd.this);
                adb.setTitle("Delete");
                adb.setMessage("You want to delete " + "\"" + adapter.getItem(position) + "\"" + "?");
                final int positionToRemove = position;
                adb.setNegativeButton("Cancel", null);
                adb.setPositiveButton("Ok", new AlertDialog.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        adapter.remove(adapter.getItem(positionToRemove));
                        adapter.notifyDataSetChanged();
                    }
                });
                adb.show();
            }
        });
        setImage();
    }

    private void add() {
        if (editText.getText().toString().isEmpty()) {
            Toast.makeText(OrderRequestAdd.this, "Enter the UPC", Toast.LENGTH_LONG).show();
        }
        else {
            String newItem = editText.getText().toString();
            if (!newItem.contains("x")) {
                Toast.makeText(OrderRequestAdd.this, "Please enter the UPC followed by x and then quantity", Toast.LENGTH_LONG).show();
            } else {
                String url_search = "http://proj-309-sd-b-5.cs.iastate.edu/database/upc_check.php?"
                        + "company_id=" + MainActivity.company_id + "&location_id=" + MainActivity.location_id
                        + "&upc=" + newItem.substring(0, newItem.indexOf('x'));

                StringRequest stringRequest = new StringRequest(Request.Method.GET, url_search, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Response = response;
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
                RequestQueue requestQueue = Volley.newRequestQueue(this);
                requestQueue.add(stringRequest);

                if (!newItem.substring(0, newItem.indexOf('x')).equals(Response)) {
                    //Toast.makeText(OrderRequestAdd.this, "Item does not exist in the company catalogue.", Toast.LENGTH_LONG).show();
                } else {
                    if (itemList.contains(newItem)) {
                        Toast.makeText(OrderRequestAdd.this, "Item is already in the list", Toast.LENGTH_LONG).show();
                    } else {
                        editText.setText("");
                        itemList.add(newItem);
                        // notify listview of data changed
                        adapter.notifyDataSetChanged();
                    }
                }
            }
        }
    }

    @Override
    public void onClick(View v) {
        if (v == buttonAdd) {
            add();
        } else if (v == buttonSubmit) {
            if (itemList.isEmpty()) {
                editText.setError("Fill out the item list");
            } else {
                finish();
                startActivity(new Intent(this, OrderRequestActivity.class));
            }
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
        AlertDialog.Builder adb = new AlertDialog.Builder(OrderRequestAdd.this, R.style.MyAlertDialogStyle);
        adb.setTitle("Create items needed list information");
        adb.setMessage("This is where you can create the list of items that you store needs. Your list includes the UPC numbers of" +
                " all the items that you are requesting from your supplier.");
        adb.setNegativeButton("OK", null);
        adb.show();
    }
}
