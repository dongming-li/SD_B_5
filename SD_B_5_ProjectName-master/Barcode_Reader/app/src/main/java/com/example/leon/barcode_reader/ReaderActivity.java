package com.example.leon.barcode_reader;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.util.HashMap;

public class ReaderActivity extends AppCompatActivity {
    private Button scan_btn;
    HashMap<String,String> barcode = new HashMap<String, String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reader);
        scan_btn = (Button) findViewById(R.id.scan_btn);
        final Activity activity = this;

        /**
         * Store the result in the hashmap
         */

        scan_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            /**
             * Respond to button clicks by starting the scanning process
             */
            public void onClick(View view) {
                IntentIntegrator integrator = new IntentIntegrator(activity);
                integrator.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES);
                integrator.setPrompt("Scan");
                integrator.setCameraId(0);
                integrator.setBeepEnabled(false);
                integrator.setBarcodeImageEnabled(true);

                /**
                Intent intent = new Intent(this, );
                intent.putExtra("Scan Result", hashmap);
                 */
                //call on the Intent Integrator method to start scanning.

                integrator.initiateScan();

            }
        });
    }

    /**
     * When they scan a barcode, it will return the scanned data to the
     * onActivityResult method of the calling activity
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if(result != null){
            if(result.getContents()==null){
                Toast.makeText(this, "Scanning cancelled", Toast.LENGTH_LONG).show();
            }
            else {
                //Put the result in the hashmap
                String a;
              //  barcode.put(result.getContents(),);
                Toast.makeText(this, result.getContents(),Toast.LENGTH_LONG).show();
                //Intent intent = getIntent();
                //String message = intent.getStringExtra();


            }
        }
        else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
}
