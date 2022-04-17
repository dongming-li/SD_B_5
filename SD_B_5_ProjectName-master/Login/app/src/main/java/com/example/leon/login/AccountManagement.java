package com.example.leon.login;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class AccountManagement extends OperationActivity implements View.OnClickListener {

    private Button buttonUserTypeGenerate;
    private Button buttonStoreGenerate;
    private Button buttonRemoveUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_management);

        buttonUserTypeGenerate = findViewById(R.id.buttonUserTypeGenerate);
        buttonStoreGenerate = findViewById(R.id.buttonStoreGenerate);
        buttonRemoveUser = findViewById(R.id.buttonRemoveUser);
        buttonUserTypeGenerate.setOnClickListener(this);
        buttonStoreGenerate.setOnClickListener(this);
        buttonRemoveUser.setOnClickListener(this);
        navDrawer();
        setImage();
    }

    private void userTypeGenerate() {
        finish();
        startActivity(new Intent(this, UserType.class));
    }
    @Override
    public void onClick(View v) {
        if (v == buttonUserTypeGenerate) {
            userTypeGenerate();
        }
        else if (v == buttonStoreGenerate){
            finish();
            startActivity(new Intent(this, TestAddressConvert.class));
        }
        else if (v == buttonRemoveUser) {
            finish();
            //startActivity(new Intent (this, RemoveUser.class ));
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
        AlertDialog.Builder adb = new AlertDialog.Builder(AccountManagement.this, R.style.MyAlertDialogStyle);
        adb.setTitle("Account Management Information");
        adb.setMessage("This is where you access your account management privileges. This includes adding and removing users and stores");
        adb.setNegativeButton("OK", null);
        adb.show();
    }
}
