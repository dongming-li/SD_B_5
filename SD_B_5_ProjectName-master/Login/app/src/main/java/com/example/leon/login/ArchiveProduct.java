package com.example.leon.login;

import android.icu.text.IDNA;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.ArrayList;
import java.util.Scanner;

public class ArchiveProduct extends AppCompatActivity
{
	private RecyclerView recyclerView;
	private RecyclerView.LayoutManager layoutManager;
	private RecyclerView.Adapter adapter;
	private ArrayList<Information> productList;
	private String locationID = "";
	static String parsedResult = "";

	private final String DATA_URL_ARCHIVE = "http://proj-309-sd-b-5.cs.iastate.edu/database/archived.php?company_id=" + MainActivity.company_id + "&location_id=";

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_archive_product);
		recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
		productList = new ArrayList<Information>();

		// Use linear layout manager
		layoutManager = new LinearLayoutManager(this);
		recyclerView.setLayoutManager(layoutManager);
		getCompletedOrder();
	}

	protected void getCompletedOrder()
	{
		locationID = MainActivity.location_id;
		String url = DATA_URL_ARCHIVE + locationID;

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
						Toast.makeText(ArchiveProduct.this, error.getMessage().toString(), Toast.LENGTH_LONG).show();
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
			return parsedResult;
		}
		else
		{
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
}
