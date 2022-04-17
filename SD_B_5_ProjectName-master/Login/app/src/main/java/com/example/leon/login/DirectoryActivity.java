package com.example.leon.login;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class DirectoryActivity extends AppCompatActivity implements View.OnClickListener {

    private Button buttonUpdate;
    private Button buttonSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_directory);

        //Initialize the Update button
        buttonUpdate = (Button) findViewById(R.id.buttonUpdate);
        //Initialize the Search button
        buttonSearch = (Button) findViewById(R.id.buttonSearch);
        buttonUpdate.setOnClickListener(this);
        buttonSearch.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        if (v == buttonUpdate) {
            finish();
            startActivity(new Intent(this, DirectoryUpdate.class));
        } else if (v == buttonSearch) {

        }
    }
}
