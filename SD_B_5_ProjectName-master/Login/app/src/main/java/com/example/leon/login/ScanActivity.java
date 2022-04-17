package com.example.leon.login;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class ScanActivity extends OperationActivity implements View.OnClickListener {

    private Button buttonScan;
    static String barcode;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan);
        buttonScan = findViewById(R.id.buttonScan);
        buttonScan.setOnClickListener(this);
        navDrawer();
        setImage();
    }

    /**
     * When they scan a barcode, it will return the scanned data to the
     * onActivityResult method of the calling activity
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            if (result.getContents() == null) {
                Toast.makeText(this, "Scanning cancelled", Toast.LENGTH_LONG).show();
            } else {
                //Toast.makeText(this, result.getContents(), Toast.LENGTH_LONG).show();
                //Get ID of the scanned item and store it in a string.
                barcode = result.getContents();
                finish();
                startActivity(new Intent(this, CheckActivity.class));
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    protected void scan() {
        final Activity activity = this;
        IntentIntegrator integrator = new IntentIntegrator(activity);
        integrator.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES);
        integrator.setPrompt("Scan");
        integrator.setCameraId(0);
        integrator.setBeepEnabled(false);
        integrator.setBarcodeImageEnabled(true);
        //call on the Intent Integrator method to start scanning.
        integrator.initiateScan();

    }

    @Override
    public void onClick(View view) {
        if(view == buttonScan){
            scan();
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
        AlertDialog.Builder adb = new AlertDialog.Builder(ScanActivity.this, R.style.MyAlertDialogStyle);
        adb.setTitle("Scan Information");
        adb.setMessage("This is your where you can scan the product to add it to the company catalogue or update the listing if it is already" +
                "registered in the system.");
        adb.setNegativeButton("OK", null);
        adb.show();
    }
}
