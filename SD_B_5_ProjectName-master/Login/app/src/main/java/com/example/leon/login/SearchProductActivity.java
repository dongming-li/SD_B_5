package com.example.leon.login;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.util.ArrayList;
import java.util.Scanner;

public class SearchProductActivity extends OperationActivity implements View.OnClickListener
{
    private EditText editTextUPCName;
    private TextView textViewResult;
	private Button buttonSearchUpc;
	private Button buttonSearchName;
	private Button buttonSearchLocation;
	private Button buttonSearchScan;
	private Button buttonArhived;
	private RecyclerView recyclerView;
	private RecyclerView.LayoutManager layoutManager;
	private RecyclerView.Adapter adapter;
	private ArrayList<Information> productList;
	static String parsedResult = "";

	private String upc;

	private final String DATA_URLUPC = "http://proj-309-sd-b-5.cs.iastate.edu/database/search.php?company_id=" + MainActivity.company_id + "&upc=";
	private final String DATA_URLNAME = "http://proj-309-sd-b-5.cs.iastate.edu/database/search.php?company_id=" + MainActivity.company_id + "&name=";
	private final String DATA_URLLOCATION = "http://proj-309-sd-b-5.cs.iastate.edu/database/search.php?company_id=" + MainActivity.company_id + "&location_id=";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_product);
		navDrawer();
		setImage();
        editTextUPCName = (EditText) findViewById(R.id.editTextUPCName);
        textViewResult = (TextView) findViewById(R.id.textViewResult);
		buttonSearchUpc = (Button) findViewById(R.id.buttonSearchUpc);
		buttonSearchName = (Button) findViewById(R.id.buttonSearchName);
		buttonSearchLocation = (Button) findViewById(R.id.buttonSearchLocation);
		buttonSearchScan = (Button) findViewById(R.id.buttonSearchScanner);
		buttonArhived = (Button) findViewById(R.id.buttonArchive);
		recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
		productList = new ArrayList<Information>();

		buttonSearchName.setOnClickListener(this);
		buttonSearchUpc.setOnClickListener(this);
		buttonSearchLocation.setOnClickListener(this);
		buttonArhived.setOnClickListener(this);
		final Activity activity = this;

		// Use linear layout manager
		layoutManager = new LinearLayoutManager(this);
		recyclerView.setLayoutManager(layoutManager);

		buttonSearchScan.setOnClickListener(new View.OnClickListener() {
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

				//call on the Intent Integrator method to start scanning.
				integrator.initiateScan();
			}
		});
    }

	protected void getDataName()
	{
		String upcName = editTextUPCName.getText().toString().trim();
		if (upcName.equals(""))
		{
			Toast.makeText(this, "Please enter a Product Name", Toast.LENGTH_LONG).show();
			return;
		}
		String url = DATA_URLNAME + editTextUPCName.getText().toString();

		StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>()
		{
			@Override
			public void onResponse(String response)
			{
				parseText(response);
				adapter = new RecyclerViewAdapter(productList);
				recyclerView.setAdapter(adapter);
			}
		},
				new Response.ErrorListener()
				{
					@Override
					public void onErrorResponse(VolleyError error)
					{
						Toast.makeText(SearchProductActivity.this, error.getMessage().toString(), Toast.LENGTH_LONG).show();
					}
				});

		RequestQueue requestQueue = Volley.newRequestQueue(this);
		requestQueue.add(stringRequest);
	}

	protected void getDataLocation()
	{
		String location = editTextUPCName.getText().toString().trim();
		if (location.equals(""))
		{
			Toast.makeText(this, "Please enter a Location ID", Toast.LENGTH_LONG).show();
			return;
		}
		String url = DATA_URLLOCATION + editTextUPCName.getText().toString();

		StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>()
		{
			@Override
			public void onResponse(String response)
			{
				parseText(response);
				adapter = new RecyclerViewAdapter(productList);
				recyclerView.setAdapter(adapter);
			}
		},
				new Response.ErrorListener()
				{
					@Override
					public void onErrorResponse(VolleyError error)
					{
						Toast.makeText(SearchProductActivity.this, error.getMessage().toString(), Toast.LENGTH_LONG).show();
					}
				});

		RequestQueue requestQueue = Volley.newRequestQueue(this);
		requestQueue.add(stringRequest);
	}

	protected void getDataScanner()
	{
		String url = DATA_URLUPC + "\"" + upc + "\"";

		StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>()
		{
			@Override
			public void onResponse(String response)
			{
				parseText(response);
				adapter = new RecyclerViewAdapter(productList);
				recyclerView.setAdapter(adapter);
			}
		},
				new Response.ErrorListener()
				{
					@Override
					public void onErrorResponse(VolleyError error)
					{
						Toast.makeText(SearchProductActivity.this, error.getMessage().toString(), Toast.LENGTH_LONG).show();
					}
				});

		RequestQueue requestQueue = Volley.newRequestQueue(this);
		requestQueue.add(stringRequest);
	}

	protected void getDataUpc()
	{
		String upcName = editTextUPCName.getText().toString().trim();
		if (upcName.equals(""))
		{
			Toast.makeText(this, "Please enter a UPC", Toast.LENGTH_LONG).show();
			return;
		}
		String url = DATA_URLUPC + "\"" + editTextUPCName.getText().toString() + "\"";

		StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>()
		{
			@Override
			public void onResponse(String response)
			{
				parseText(response);
				adapter = new RecyclerViewAdapter(productList);
				recyclerView.setAdapter(adapter);
			}
		},
				new Response.ErrorListener()
				{
					@Override
					public void onErrorResponse(VolleyError error)
					{
						Toast.makeText(SearchProductActivity.this, error.getMessage().toString(), Toast.LENGTH_LONG).show();
					}
				});

		RequestQueue requestQueue = Volley.newRequestQueue(this);
		requestQueue.add(stringRequest);
	}

	private String parseText(String result)
	{
		Scanner scanner = new Scanner(result);
		productList = new ArrayList<Information>();
		String upc = "";
		String name = "";
		String quantity = "";
		String description = "";
		String binID = "";
		String manufacturerPrice = "";
		String msrp = "";
		String locationID = "";
		String archive = "";
		parsedResult = "";

		if (result.charAt(0) == '0')
		{
			textViewResult.setText("0 result found ");
			return parsedResult;
		}
		else
		{
			textViewResult.setText("");
			while (scanner.hasNextLine())
			{
				String line = scanner.nextLine();
				String[] information;
				information = line.split("-");

				upc = information[0];
				name = information[1];
				quantity = information[2];
				description = information[3];
				binID = information[4];
				manufacturerPrice = information[5];
				msrp = information[6];
				locationID = information[7];
				archive = information[8];

				Information info = new Information(upc, name, quantity, description, binID, manufacturerPrice, msrp, locationID, archive);
				productList.add(info);
			}

			for (Information q : productList)
			{
				parsedResult += "\n" + q.getUpc() + "\n" + q.getName() + "\n" + q.getQuantity() + "\n" + q.getDescription()
						+ "\n" + q.getBinID() + "\n" + q.getManufacturerPrice()
						+ "\n" + q.getMsrp() + "\n" + q.getLocationID() + "\n" + q.getArchive() + "\n";
			}
			return parsedResult;
		}
	}

	public void onClick(View view)
	{
		if (view == buttonSearchUpc)
			getDataUpc();
		else if (view == buttonSearchName)
			getDataName();
		else if (view == buttonSearchLocation)
			getDataLocation();
		else if (view == buttonArhived)
			startActivity(new Intent(SearchProductActivity.this, ArchiveProduct.class));
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
			else
			{
				upc = result.getContents();
				getDataScanner();
			}
		}
		else {
			super.onActivityResult(requestCode, resultCode, data);
		}
	}

	public String getParsedResult()
	{
		return parsedResult;
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
		AlertDialog.Builder adb = new AlertDialog.Builder(SearchProductActivity.this, R.style.MyAlertDialogStyle);
		adb.setTitle("Search Item Information");
		adb.setMessage("This is where you can search the items that are in the inventory of your store by entering different fields.");
		adb.setNegativeButton("OK", null);
		adb.show();
	}
}
