package com.example.leon.login;

import android.location.Address;
import android.location.Geocoder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class TestAddressConvert extends AppCompatActivity implements View.OnClickListener {

    private EditText Address;
    private Button Convert;
    private LatLng latLng;
    private String name;
    private Button Test;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_address_convert);
        Address = findViewById(R.id.address);
        Convert = findViewById(R.id.convert);
        Convert.setOnClickListener(this);
        Test = findViewById(R.id.test);
        Test.setOnClickListener(this);
    }

    private void getLatLong(String strAddress) throws IOException {
        Geocoder geocoder = new Geocoder(this);
        List<Address> address = geocoder.getFromLocationName(strAddress, 1);
        if (address == null) {
            Toast.makeText(this, "Error: Invalid address", Toast.LENGTH_LONG).show();
            return;
        }
            Address location = address.get(0);
            latLng = new LatLng(location.getLatitude(), location.getLongitude());
    }

    @Override
    public void onClick(View view) {
        if (view == Convert) {
            if (Address.getText().toString().isEmpty()) {
                Toast.makeText(TestAddressConvert.this, "Enter the address", Toast.LENGTH_LONG).show();
            } else {
                try {
                    getLatLong(Address.getText().toString());
                    Toast.makeText(TestAddressConvert.this, latLng.toString(), Toast.LENGTH_LONG).show();
                } catch (IOException e) {
                    Toast.makeText(this, "Error", Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }
            }
        }
        else if (view == Test) {
            test1();
            test2();
        }
    }

    private void test1 () {
        name = "JESUS";
    }

    private void test2() {
        Toast.makeText(getApplicationContext(), name, Toast.LENGTH_LONG).show();
    }


}
