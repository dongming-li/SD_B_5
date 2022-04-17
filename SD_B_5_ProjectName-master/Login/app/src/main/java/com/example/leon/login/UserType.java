package com.example.leon.login;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
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
import com.scottyab.aescrypt.AESCrypt;

import java.security.GeneralSecurityException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class UserType extends OperationActivity implements View.OnClickListener {

    private Button buttonEmployee;
    private Button buttonStoreManager;
    private Button buttonWarehouseManager;
    private Button buttonAdmin;
    private EditText email_address;
    private EditText location_id;
    private String encryptedCode;
    private ProgressBar progressBar;
    private String url = "http://proj-309-sd-b-5.cs.iastate.edu/database/add_user_type.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_type);
        email_address = (EditText) findViewById(R.id.email_address);
        location_id = (EditText) findViewById(R.id.location_id);
        buttonEmployee = (Button) findViewById(R.id.buttonEmployee);
        buttonStoreManager = (Button) findViewById(R.id.buttonStoreManager);
        buttonWarehouseManager = (Button) findViewById(R.id.buttonWarehouseManager);
        buttonAdmin = (Button) findViewById(R.id.buttonAdmin);
        progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.INVISIBLE);
        buttonEmployee.setOnClickListener(this);
        buttonStoreManager.setOnClickListener(this);
        buttonWarehouseManager.setOnClickListener(this);
        buttonAdmin.setOnClickListener(this);
        navDrawer();
        setImage();

        /**
        if (MainActivity.company_id == null) {
            MainActivity.company_id = CompanyRegistration.company_id;
        }
         */
    }
    private void generateEmployee() {

        String password = "password";
        Random r = new Random();
        int rand = r.nextInt(1000000);
        String code = MainActivity.company_id + "-" + location_id.getText().toString() + "-" + "1" + "-"+ rand;
        try {
            //code is the message you want to encrypt.
            //password is the string you provide to the user.
            encryptedCode = AESCrypt.encrypt(password, code);
            volley();
        } catch (GeneralSecurityException e) {
            //handle error
        }
    }

    private void generateStoreManager() {

        String password = "password";
        Random r = new Random();
        int rand = r.nextInt(1000000);
        String code = MainActivity.company_id + "-" + location_id.getText().toString() + "-" + "2" + "-"+ rand;
        try {
            //code is the message you want to encrypt.
            //password is the string you provide to the user.
            encryptedCode = AESCrypt.encrypt(password, code);
            volley();
        } catch (GeneralSecurityException e) {
            //handle error
        }
    }

    private void generateWarehouseManager() {

        String password = "password";
        Random r = new Random();
        int rand = r.nextInt(1000000);
        String code = MainActivity.company_id + "-" + location_id.getText().toString() + "-" + "3" + "-"+ rand;
        try {
            //code is the message you want to encrypt.
            //password is the string you provide to the user.
            encryptedCode = AESCrypt.encrypt(password, code);
            volley();
        } catch (GeneralSecurityException e) {
            //handle error
        }
    }

    private void generateAdmin() {

        String password = "password";
        Random r = new Random();
        int rand = r.nextInt(1000000);
        String code = MainActivity.company_id + "-" + location_id.getText().toString() + "-" + "4" + "-"+ rand;
        try {
            //code is the message you want to encrypt.
            //password is the string you provide to the user.
            encryptedCode = AESCrypt.encrypt(password, code);
            volley();
        } catch (GeneralSecurityException e) {
            //handle error
        }
    }

    @Override
    public void onClick(View v) {
        if (v == buttonEmployee) {
            generateEmployee();
        } else if (v == buttonStoreManager) {
            generateStoreManager();
        } else if (v == buttonWarehouseManager) {
            generateWarehouseManager();
        } else if (v == buttonAdmin) {
            generateAdmin();
        }
    }

    private void volley() {
        if (email_address.getText().toString().isEmpty()) {
            email_address.setError("Email address field is empty");
        } else if (!email_address.getText().toString().matches("^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$")) {
            email_address.setError("Please enter a valid email address");
        } else if (location_id.getText().toString().isEmpty()) {
            location_id.setError("Please enter a location id");
        } else {
            progressBar.setVisibility(View.VISIBLE);
            RequestQueue requestQueue = Volley.newRequestQueue(this);
            StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    progressBar.setVisibility(View.INVISIBLE);
                    Toast.makeText(getApplicationContext(), "Access code generated", Toast.LENGTH_LONG).show();
                    sendEmail();
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
                    parameters.put("email_address", email_address.getText().toString());
                    parameters.put("encryption_code", encryptedCode);
                    return parameters;
                }
            };
            requestQueue.add(request);
        }
    }

    private void sendEmail() {
        Log.i("Send email", "");
        String[] TO = {email_address.getText().toString()};
        String[] CC = {""};
        Intent emailIntent = new Intent(Intent.ACTION_SEND);

        emailIntent.setData(Uri.parse("mailto:"));
        emailIntent.setType("text/plain");
        emailIntent.putExtra(Intent.EXTRA_EMAIL, TO);
        emailIntent.putExtra(Intent.EXTRA_CC, CC);
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Your Access Code");
        emailIntent.putExtra(Intent.EXTRA_TEXT, "This email contains your account access code. Please use " +
                "it during the registration." + "\n" + "\n" +
                "Access Code: " + encryptedCode);

        try {
            startActivity(Intent.createChooser(emailIntent, "Send mail..."));
            finish();
            Log.i("Finished sending email.", "");
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(UserType.this, "There is no email client installed.", Toast.LENGTH_SHORT).show();
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
        AlertDialog.Builder adb = new AlertDialog.Builder(UserType.this, R.style.MyAlertDialogStyle);
        adb.setTitle("Generate User Information");
        adb.setMessage("This is where you can create users with different hierarchy roles including regular employee, store manager, " +
                "warehouse manager, and company admin. You have to provide the email address of the person who was hired and the location" +
                " of the store he/she will be assigned to for the job. ");
        adb.setNegativeButton("OK", null);
        adb.show();
    }
}
