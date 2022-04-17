package com.example.leon.login;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {


    /**
     * Declare the views present in the Login activity xml.
     */
    private Button buttonSignIn;  //Sign in
    private EditText editTextEmail; //Email
    private EditText editTextPassword; //Password
    //private EditText editTextConfirmPassword;
    private TextView textViewSignUp; //Sign up
    //A progress bar since we use internet to perform
    // operation and it might take some time
    private TextView textViewForgotPassword;
    private ProgressBar progressBar;
    //Firebase authentication object.
    private FirebaseAuth firebaseAuth;
    private int attempts = 3;

    static String Name;
    static String companyID;
    static String locationID;
    static String usertype;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //Initialize the firebase auth object required
        //for user authentication.
        firebaseAuth = FirebaseAuth.getInstance();
        //Check if the user is already logged in or not.
        // If not null then (i.e., logged in) then
        if (firebaseAuth.getCurrentUser() != null) {
            //that means user is already logged in
            //so close this activity
            finish();
            //do some activity in the Operation Activity class.
            startActivity(new Intent(getApplicationContext(), OperationActivity.class));
        }

        /**
         * Initialize the views by using the id's in xml.
         */
        editTextEmail = (EditText) findViewById(R.id.editTextEmail);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);
        buttonSignIn = (Button) findViewById(R.id.buttonSignin);
        textViewSignUp = (TextView) findViewById(R.id.textViewSignUp);
        textViewForgotPassword = (TextView) findViewById(R.id.textViewForgotPassword);
        //Initialize the progress bar.
        progressBar = (ProgressBar) findViewById(R.id.progressbar);
        progressBar.setVisibility(View.INVISIBLE); //To Hide ProgressBar
        //Add the listener to the button and textview.
        //Listener is on the same class, so just pass "this" as the argument.
        buttonSignIn.setOnClickListener(this);
        textViewSignUp.setOnClickListener(this);
        textViewForgotPassword.setOnClickListener(this);
    }


    private void userLogin() {

        //getting email and password from edit texts
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();


        //check for email: empty string
        if (email.isEmpty()) {
            //email string is empty then display message:
            editTextEmail.setError("Please enter your email address");
            return;
        } else if (!email.matches("^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$")) {
            //email string is invalid then display message:
            editTextEmail.setError("Please enter a valid email address");
            return;
        }
        //check for password: empty string
        else if (password.isEmpty()) {
            //password string is empty or length is not right display message:
            editTextPassword.setError("Please enter a password");
            return;
        }
        //Otherwise email and password are fine, so
        // show the user their account registration progress using
        // the progress dialog.

        progressBar.setVisibility(View.VISIBLE);// To Show ProgressBar

        //Log in the user.
        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        //if the task is successfull
                        if (task.isSuccessful()) {
                            //Get info about the user.
                            retrieveInfo();
                            retrieveName();
                            progressBar.setVisibility(View.INVISIBLE);
                            //Do some profile activity.
                            finish();//Close this activity first.
                            //Start new activity using intent object.
                            //Can't use "this" since we are inside onCompleteListener.
                            //Use get application context instead that will
                            //return the context for the entire application.
                            startActivity(new Intent(getApplicationContext(), OperationActivity.class));
                        } else {
                            ////User not registered. Display some message
                            Toast.makeText(LoginActivity.this, "Invalid email/password", Toast.LENGTH_LONG).show();
                            progressBar.setVisibility(View.INVISIBLE);
                            //Login attempts decrease by 1.
                            attempts--;
                            attempt(); //Call the helper attempt method.
                        }
                    }
                });

    }

    private void attempt() {
        //If user runs out of login attempts then
        if (attempts == 0) {
            //Disable the sign in button for 30 seconds.
            buttonSignIn.setEnabled(false);
            Toast.makeText(LoginActivity.this, "You made too many incorrect login attempts. Wait for some time", Toast.LENGTH_LONG).show();
            buttonSignIn.postDelayed(new Runnable() {
                public void run() {
                    buttonSignIn.setEnabled(true);
                }
            }, 1 * 30 * 1000);
        }
    }

    private void forgotPassword() {
        if (editTextEmail.getText().toString().isEmpty()){
            Toast.makeText(getApplicationContext(), "Error: Email Address field is empty", Toast.LENGTH_LONG).show();
            return;
        }
        else {
        AlertDialog.Builder adb = new AlertDialog.Builder(LoginActivity.this, R.style.MyAlertDialogStyle);
        adb.setTitle("Reset Password");
        adb.setMessage("Do you want to reset password for " + "\"" + editTextEmail.getText().toString() + "\"" + "?");
        adb.setNegativeButton("Cancel", null);
        adb.setPositiveButton("Ok", new AlertDialog.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                firebaseAuth.sendPasswordResetEmail(editTextEmail.getText().toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(getApplicationContext(), "Check email to reset your password!", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getApplicationContext(), "Error: Invalid Email Address. Email not registered!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
        adb.show();
        }
    }

    private void retrieveName() {
        String url_search = "http://proj-309-sd-b-5.cs.iastate.edu/database/full_name.php?email=" + FirebaseAuth.getInstance().getCurrentUser().getEmail();
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url_search, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Name = response;
                Toast.makeText(getApplicationContext(), Name + " signed in", Toast.LENGTH_LONG).show();
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

    private void retrieveInfo(){
        String url_search = "http://proj-309-sd-b-5.cs.iastate.edu/database/user_lookup_email.php?email=" + FirebaseAuth.getInstance().getCurrentUser().getEmail();
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url_search, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                companyID = response.substring(0, response.indexOf(","));
                String temp1 = response.substring(response.indexOf(",") + 1);
                locationID = temp1.substring(0, temp1.indexOf(","));
                usertype = temp1.substring(temp1.indexOf(",") + 1);
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

    @Override
    public void onClick(View view) {
        if (view == buttonSignIn) {
            userLogin(); //helper method.
        } else if (view == textViewSignUp) {
            //close this activity.
            finish();
            //Start new activity using Intent messaging object that
            //requests an action from another app component.
            startActivity(new Intent(this, MainActivity.class));
        } else if (view == textViewForgotPassword) {
            forgotPassword();
        }
    }
}

