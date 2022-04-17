package com.example.leon.login;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.ArrayList;
import java.util.Scanner;

public class SearchOrderActivity extends OperationActivity implements View.OnClickListener
{
	private TextView textViewResult;
	private RecyclerView recyclerView;
	private RecyclerView.LayoutManager layoutManager;
	private RecyclerView.Adapter adapter;
	private ArrayList<OrderInformation> orderList;
	static String parsedResult = "";

	private final String DATA_URL_ORDER_PENDING = "http://proj-309-sd-b-5.cs.iastate.edu/database/search_order.php?company_id=" +
			MainActivity.company_id + "&location_id=" + MainActivity.location_id + "&status=1";

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search_order);
		navDrawer();
		setImage();
		textViewResult = (TextView) findViewById(R.id.textViewResult);
		recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
		orderList = new ArrayList<OrderInformation>();

		// Use linear layout manager
		layoutManager = new LinearLayoutManager(this);
		recyclerView.setLayoutManager(layoutManager);
		getPendingOrder();
	}

	protected void getPendingOrder()
	{
		String url = DATA_URL_ORDER_PENDING;

		StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>()
		{
			@Override
			public void onResponse(String response)
			{
				parseText(response);
				adapter = new OrderRecyclerViewAdapter(orderList);
				recyclerView.setAdapter(adapter);
			}
		},
				new Response.ErrorListener()
				{
					@Override
					public void onErrorResponse(VolleyError error)
					{
						Toast.makeText(SearchOrderActivity.this, error.getMessage().toString(), Toast.LENGTH_LONG).show();
					}
				});
		RequestQueue requestQueue = Volley.newRequestQueue(this);
		requestQueue.add(stringRequest);
	}

	private String parseText(String result)
	{
		Scanner scanner = new Scanner(result);
		orderList = new ArrayList<OrderInformation>();
		String orderID = "";
		String upc = "";
		String status = "";
		String name = "";
		String quantity = "";
		String locationID = "";
		String destinationID = "";
		String deliveryDate = "";
		parsedResult = "";

		if (result.charAt(0) == 'N')
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
				information = line.split(",");

				orderID = information[0];
				upc = information[1];
				name = information[2];
				quantity = information[3];
				locationID = information[4];
				destinationID = information[5];
				deliveryDate = information[6];
				status = information[7];

				OrderInformation info = new OrderInformation(orderID, upc, status, name, quantity, locationID, destinationID, deliveryDate);
				orderList.add(info);
			}

			for (OrderInformation q : orderList)
			{
				parsedResult += "\n" + q.getOrderID() + "\n" + q.getUpc() + "\n" + q.getStatus() + "\n" + q.getName()
						+ "\n" + q.getQuantity() + "\n" + q.getLocationID()
						+ "\n" + q.getDestinationID() + "\n" + q.getLocationID() + "\n";
			}
			return parsedResult;
		}
	}

	public void onClick(View view)
	{
//		if (view == buttonPendingOrder)
//			getPendingOrder();
//		else if (view == buttonCompletedOrder)
//			startActivity(new Intent(SearchOrderActivity.this, CompletedOrder.class));
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
		AlertDialog.Builder adb = new AlertDialog.Builder(SearchOrderActivity.this, R.style.MyAlertDialogStyle);
		adb.setTitle("Pending Orders Information");
		adb.setMessage("This is where you can view the pending orders submitted from stores to your warehouse. Once you shipped" +
				"the necessary items, you can change the status of the order to completed.");
		adb.setNegativeButton("OK", null);
		adb.show();
	}
}
