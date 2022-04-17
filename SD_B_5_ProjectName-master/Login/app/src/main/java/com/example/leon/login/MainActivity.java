package com.example.leon.login;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.text.TextWatcher;

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
import com.scottyab.aescrypt.AESCrypt;

import java.security.GeneralSecurityException;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    /**
     * Declare the views present in the Main activity xml.
     */
    private Button buttonSignup;  //Registration
    private EditText editTextEmail; //Email
    private EditText editTextPassword; //Password
    private EditText editTextConfirmPassword; //Confirm password.
    private EditText editTextAccessCode; //Access code
    private TextView textViewSignin; //Sign in
    private TextView textViewPasswordStrength;
    //A progress bar since we use internet to perform
    // operation and it might take some time
    private ProgressBar pb;
    //Firebase authentication object.
    private FirebaseAuth firebaseAuth;
    private ProgressBar progressBar;
    private Editable temp;
    private String password;

    static String company_id;
    static String location_id;
    static String userType;
    private String url = "http://proj-309-sd-b-5.cs.iastate.edu/database/search_user_type.php?email=";
    private String Response = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
        editTextConfirmPassword = (EditText) findViewById(R.id.editTextConfirmPassword);
        editTextAccessCode = (EditText) findViewById(R.id.editTextAccessCode);
        textViewSignin = (TextView) findViewById(R.id.textViewSignin);
        buttonSignup = (Button) findViewById(R.id.buttonRegister);
        //Initialize the password strength indicator
        textViewPasswordStrength = (TextView) findViewById(R.id.textViewPasswordStrength);
        //Initialize the progress bar.
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        //Initialize the progress bar
        pb = (ProgressBar) findViewById(R.id.pb);
        pb.setVisibility(View.INVISIBLE);
        //Add the listener to the button and textview.
        //Listener is on the same class, so just pass "this" as the argument.
        buttonSignup.setOnClickListener(this);
        textViewSignin.setOnClickListener(this);

        editTextEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (editTextEmail.getText().toString().isEmpty()) {
                    editTextEmail.setError("Email Address field is empty");
                } else if (!editTextEmail.getText().toString().matches("^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$")) {
                    editTextEmail.setError("Invalid email address entered");
                } else {
                    editTextPassword.setError(null);
                }
            }
        });
        editTextPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                if (editTextPassword.getText().toString().length() == 0) {
                    editTextPassword.setError("Enter your password!");
                }
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (editTextPassword.getText().toString().length() < 6) {
                    editTextPassword.setError("Password should be of at least 6 characters");
                } else {
                    editTextPassword.setError(null);
                }
                temp = s;
                calculate();
            }
        });
        editTextConfirmPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!editTextConfirmPassword.getText().toString().equals(password)) {
                    editTextConfirmPassword.setError("Passwords do not match");
                }
            }
        });
    }

    /**
     * Score calculator to compute the password strength.
     */
    private void calculate() {
        //getting email and passwords from edit texts
        //email = editTextEmail.getText().toString().trim();
        password = editTextPassword.getText().toString().trim();
        String s = temp.toString().trim();
        int score = passwordStrengthCalculator(s);
        if (temp.length() >= 6 && temp.length() <= 9) {
            score += 10;
        } else if (temp.length() >= 10 && temp.length() <= 13) {
            score += 30;
        } else if (temp.length() >= 14 && temp.length() <= 17) {
            score += 50;
        } else if (temp.length() >= 18 && temp.length() <= 20) {
            score += 70;
        }

        if (temp.length() == 0) {
            textViewPasswordStrength.setText("Not Entered");
            progressBar.setProgress(0);
        }
        if (temp.length() >= 1 && temp.length() <= 5) {
            textViewPasswordStrength.setText("Invalid Password");
            progressBar.setProgress(0);
        }
        if (temp.length() >= 6 && temp.length() <= 20) {
            if (score <= 40) {
                textViewPasswordStrength.setText("Weak Password");
                progressBar.setProgress(25);
            } else if (score > 40 && score <= 80) {
                textViewPasswordStrength.setText("Reasonable Password");
                progressBar.setProgress(50);
            } else if (score > 80 && score <= 120) {
                textViewPasswordStrength.setText("Strong Password");
                progressBar.setProgress(75);
            } else if (score > 120) {
                textViewPasswordStrength.setText("Very Strong Password");
                progressBar.setProgress(100);
            }
        }
        if (temp.length() > 20) {
            textViewPasswordStrength.setText("Reduce password length to 20");
            progressBar.setProgress(0);
        }
    }

    private int passwordStrengthCalculator(String s) {
        int score = 0;
        String[] regex = {
                ".*[a-z]+.*", // lower case
                ".*[A-Z]+.*", // upper case
                ".*[\\d]+.*", // digits
                ".*[!,@#$%^&*?_~]+.*" // symbols
        };
        if (s.matches(regex[0])) {
            score += 20;
        }
        if (s.matches(regex[1])) {
            score += 20;
        }
        if (s.matches(regex[2])) {
            score += 20;
        }
        if (s.matches(regex[3])) {
            score += 20;
        }
        return score;
    }

    /**
     * Verifies if the access code that was provided to the user email
     * is being used in conjunction with that email during registration.
     */
    private void accessCodeCheck() {

        String url_search = url + editTextEmail.getText().toString();
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url_search, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response.equals(editTextAccessCode.getText().toString())) {
                    Response = response;
                    //Correct user, so find his userType.
                    retrieveUserType();
                } else {
                    editTextAccessCode.setError("Invalid Access Code");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "Incorrect Email Address", Toast.LENGTH_LONG).show();
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    /**
     * After user is verified, it decrypts their access code to
     * reveal their user type which is further used to set
     * up their navigation and functionality within the app based on their
     * user type permissions.
     */
    private void retrieveUserType() {

        String password = "password";
        String encryptedMsg = editTextAccessCode.getText().toString();
        try {
            //company_id + location_id + userType + some random int.
            String code = AESCrypt.decrypt(password, encryptedMsg);
            company_id = code.substring(0, code.indexOf("-"));
            String temp1 = code.substring(code.indexOf("-") + 1);
            location_id = temp1.substring(0, temp1.indexOf("-"));
            String temp2 = temp1.substring(temp1.indexOf("-") + 1);
            userType = temp2.substring(0, temp2.indexOf("-"));

        } catch (GeneralSecurityException e) {
            Toast.makeText(this, "Error", Toast.LENGTH_LONG).show();
            //Stops further execution.
            return;
        }
    }

    private void register() {
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();
        final String confirm_password = editTextConfirmPassword.getText().toString().trim();
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
        //check for password: empty string or length is between 6 and 20 alphanumeric characters.
        else if (password.isEmpty() || password.length() < 6 || password.length() > 20) {
            //password string is empty or length is not right display message:
            editTextPassword.setError("Please enter a password between 6 and 20 characters");
            return;
        }
        //check if password contains the email address or password contains domain.
        else if (password.contains(email) || password.contains(email.substring(0, email.indexOf("@")))) {
            progressBar.setProgress(0);
            textViewPasswordStrength.setText("Very weak Password");
            editTextPassword.setError("Password contains username");
            return;
        }
        //check for password: empty string or length is between 6 and 20 alphanumeric characters.
        else if (confirm_password.isEmpty()) {
            //password string is empty or length is not right display message:
            editTextConfirmPassword.setError("Please confirm your password again");
            return;
        }
        //Check for access code: empty string.
        else if (editTextAccessCode.getText().toString().isEmpty()) {
            //Access Code is empty
            editTextAccessCode.setError("Please enter the access code you were provided via email");
            //Stops further execution
            return;
        }
        //userType 5 provide by developers. Only meant for superAdmin who creates the
        //company and then creates the admin.
        else if (editTextAccessCode.getText().toString().equals("5")) {
            if (confirm_password.equals(password)) {
                pb.setVisibility(View.VISIBLE);
                firebaseAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                //checking if success
                                if (task.isSuccessful()) {
                                    //Uses successfully registered. Display a message.
                                    pb.setVisibility(View.INVISIBLE);
                                    Toast.makeText(MainActivity.this, "Successfully Registered", Toast.LENGTH_LONG).show();
                                    //Close this activity.
                                    finish();
                                    startActivity(new Intent(MainActivity.this, CompanyRegistration.class));
                                } else {
                                    pb.setVisibility(View.INVISIBLE);
                                    ////User not registered. Display some message

                                    Toast.makeText(MainActivity.this, "Registration Error. Enter a valid email address", Toast.LENGTH_LONG).show();
                                }
                            }
                        });
            } else
                Toast.makeText(MainActivity.this, "Passwords do not match. Try again", Toast.LENGTH_LONG).show();
        } else {
            //Otherwise email and password are fine, so
            // show the user their account registration progress using
            // the progress dialog.
            //Call the method for access code check.
            accessCodeCheck();
            //Use the firebase auth object to register the user
            //to the server.
            //By adding on complete listener, our listener will be able to
            //take in the current activity and will execute the oncomplete method
            //gets completed when registration is completed using the Task object.
            if (Response.equals(editTextAccessCode.getText().toString())) {
                if (confirm_password.equals(password)) {
                    pb.setVisibility(View.VISIBLE);
                    firebaseAuth.createUserWithEmailAndPassword(email, password)
                            .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    //checking if success
                                    if (task.isSuccessful()) {
                                        pb.setVisibility(View.INVISIBLE);
                                        //Uses successfully registered. Display a message.
                                        Toast.makeText(MainActivity.this, "Successfully Registered", Toast.LENGTH_LONG).show();
                                        //Close this activity.
                                        finish();
                                        startActivity(new Intent(MainActivity.this, DirectoryAdd.class));
                                    } else {
                                        ////User not registered. Display some message
                                        Toast.makeText(MainActivity.this, "Registration Error. Enter a valid email address", Toast.LENGTH_LONG).show();
                                    }
                                }
                            });
                } else
                    Toast.makeText(MainActivity.this, "Passwords do not match. Try again", Toast.LENGTH_LONG).show();
            }
        }

    }

    @Override
    /**
     *  On click activity method that decides what happens when user
     *  clicks on the button or signin.
     */
    public void onClick(View view) {

        if (view == buttonSignup) {

            register();
        } else if (view == textViewSignin) {
            //Start new Login Ativity using Intent messaging object that
            //requests an action from another app component.
            startActivity(new Intent(this, LoginActivity.class));
        }
    }
}