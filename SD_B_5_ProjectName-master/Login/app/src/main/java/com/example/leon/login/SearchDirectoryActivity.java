package com.example.leon.login;

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

import java.util.ArrayList;
import java.util.Scanner;

public class SearchDirectoryActivity extends OperationActivity implements View.OnClickListener
{
	private EditText editTextUserId;
	private TextView textViewResult;
	private Button buttonSearchUserId;
	private Button buttonSearchLastName;
	private Button buttonSearchFirstName;
	private Button buttonSearchLocationId;
	private RecyclerView recyclerView;
	private RecyclerView.LayoutManager layoutManager;
	private RecyclerView.Adapter adapter;
	private ArrayList<DirectoryInformation> userList;

	private final String DATA_URL_USER_ID = "http://proj-309-sd-b-5.cs.iastate.edu/database/search_directory.php?company_id=" + MainActivity.company_id + "&user_id=";
	private final String DATA_URL_LAST_NAME = "http://proj-309-sd-b-5.cs.iastate.edu/database/search_directory.php?company_id=" + MainActivity.company_id + "&last_name=";
	private final String DATA_URL_FIRST_NAME = "http://proj-309-sd-b-5.cs.iastate.edu/database/search_directory.php?company_id=" + MainActivity.company_id + "&first_name=";
	private final String DATA_URL_LOCATION_ID = "http://proj-309-sd-b-5.cs.iastate.edu/database/search_directory.php?company_id=" + MainActivity.company_id + "&location_id=";

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search_directory);
		navDrawer();
		setImage();
		editTextUserId = (EditText) findViewById(R.id.editTextUserId);
		textViewResult = (TextView) findViewById(R.id.textViewResult);
		buttonSearchUserId = (Button) findViewById(R.id.buttonSearchUserId);
		buttonSearchLastName = (Button) findViewById(R.id.buttonSearchLastName);
		buttonSearchFirstName = (Button) findViewById(R.id.buttonSearchFirstName);
		buttonSearchLocationId = (Button) findViewById(R.id.buttonSearchLocationId);
		recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
		userList = new ArrayList<DirectoryInformation>();

		buttonSearchUserId.setOnClickListener(this);
		buttonSearchLastName.setOnClickListener(this);
		buttonSearchFirstName.setOnClickListener(this);
		buttonSearchLocationId.setOnClickListener(this);

		// Use linear layout manager
		layoutManager = new LinearLayoutManager(this);
		recyclerView.setLayoutManager(layoutManager);
	}

	protected void getDataUserId()
	{
		final String userId = editTextUserId.getText().toString().trim();
		if (userId.equals(""))
		{
			Toast.makeText(this, "Please enter a User ID", Toast.LENGTH_LONG).show();
			return;
		}
		String url = DATA_URL_USER_ID + editTextUserId.getText().toString();

		StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>()
		{
			@Override
			public void onResponse(String response)
			{
				parseText(response);
				adapter = new DirectoryRecyclerViewAdapter(userList);
				recyclerView.setAdapter(adapter);
			}
		},
				new Response.ErrorListener()
				{
					@Override
					public void onErrorResponse(VolleyError error)
					{
						Toast.makeText(SearchDirectoryActivity.this, error.getMessage().toString(), Toast.LENGTH_LONG).show();
					}
				});

		RequestQueue requestQueue = Volley.newRequestQueue(this);
		requestQueue.add(stringRequest);
	}

	protected void getDataLastName()
	{
		final String lastName = editTextUserId.getText().toString().trim();

		if (lastName.equals(""))
		{
			Toast.makeText(this, "Please enter a Last Name", Toast.LENGTH_LONG).show();
			return;
		}
		String url = DATA_URL_LAST_NAME + editTextUserId.getText().toString();

		StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>()
		{
			@Override
			public void onResponse(String response)
			{
				parseText(response);
				adapter = new DirectoryRecyclerViewAdapter(userList);
				recyclerView.setAdapter(adapter);
			}
		},
				new Response.ErrorListener()
				{
					@Override
					public void onErrorResponse(VolleyError error)
					{
						Toast.makeText(SearchDirectoryActivity.this, error.getMessage().toString(), Toast.LENGTH_LONG).show();
					}
				});

		RequestQueue requestQueue = Volley.newRequestQueue(this);
		requestQueue.add(stringRequest);
	}

	protected void getDataFirstName()
	{
		final String firstName = editTextUserId.getText().toString().trim();
		if (firstName.equals(""))
		{
			Toast.makeText(this, "Please enter a First Name", Toast.LENGTH_LONG).show();
			return;
		}
		String url = DATA_URL_FIRST_NAME + editTextUserId.getText().toString();

		StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>()
		{
			@Override
			public void onResponse(String response)
			{
				parseText(response);
				adapter = new DirectoryRecyclerViewAdapter(userList);
				recyclerView.setAdapter(adapter);
			}
		},
				new Response.ErrorListener()
				{
					@Override
					public void onErrorResponse(VolleyError error)
					{
						Toast.makeText(SearchDirectoryActivity.this, error.getMessage().toString(), Toast.LENGTH_LONG).show();
					}
				});

		RequestQueue requestQueue = Volley.newRequestQueue(this);
		requestQueue.add(stringRequest);
	}

	protected void getDataLocationId()
	{
		final String locationId = editTextUserId.getText().toString().trim();
		if (locationId.equals(""))
		{
			Toast.makeText(this, "Please enter a Location ID", Toast.LENGTH_LONG).show();
			return;
		}
		String url = DATA_URL_LOCATION_ID + editTextUserId.getText().toString();

		StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>()
		{
			@Override
			public void onResponse(String response)
			{
				parseText(response);
				adapter = new DirectoryRecyclerViewAdapter(userList);
				recyclerView.setAdapter(adapter);
			}
		},
				new Response.ErrorListener()
				{
					@Override
					public void onErrorResponse(VolleyError error)
					{
						Toast.makeText(SearchDirectoryActivity.this, error.getMessage().toString(), Toast.LENGTH_LONG).show();
					}
				});

		RequestQueue requestQueue = Volley.newRequestQueue(this);
		requestQueue.add(stringRequest);
	}

	private String parseText(String result)
	{
		Scanner scanner = new Scanner(result);
		userList = new ArrayList<DirectoryInformation>();
		String userID = "";
		String locationID = "";
		String userType = "";
		String firstName = "";
		String lastName = "";
		String email = "";
		String phoneNumber = "";
		String address = "";
		String state = "";
		String city = "";
		String zipcode = "";
		String parsedResult = "";

		if (result.charAt(0) == '0')
		{
			textViewResult.setText("0 result found ");
			return parsedResult;
		}

		textViewResult.setText("");

		while (scanner.hasNextLine())
		{
			String line = scanner.nextLine();
			String[] information;
			information = line.split("-");

			userID = information[0];
			locationID = information[1];
			userType = information[2];
			firstName = information[3];
			lastName = information[4];
			email = information[5];
			phoneNumber = information[6];
			address = information[7];
			state = information[8];
			city = information[9];
			zipcode = information[10];

			DirectoryInformation info = new DirectoryInformation(userID, locationID, userType, firstName, lastName, email, phoneNumber, address, state, city, zipcode);
			userList.add(info);
		}

		for (DirectoryInformation q : userList)
		{
			parsedResult += "\n" + q.getUserID() + "\n" +q.getLocationID()
					+ "\n" + q.getUserType()+ "\n" + q.getFirstName()
					+ "\n" + q.getLastName() + "\n" + q.getEmail()
					+ "\n" + q.getPhoneNumber() + "\n" + q.getState()
					+ "\n" + q.getCity() + "\n" + q.getZipcode();
		}
		return parsedResult;
	}

	public void onClick(View view)
	{
		if (view == buttonSearchUserId)
			getDataUserId();
		else if (view == buttonSearchLastName)
			getDataLastName();
		else if (view == buttonSearchFirstName)
			getDataFirstName();
		else if (view == buttonSearchLocationId)
			getDataLocationId();
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
		AlertDialog.Builder adb = new AlertDialog.Builder(SearchDirectoryActivity.this, R.style.MyAlertDialogStyle);
		adb.setTitle("Search Directory Information");
		adb.setMessage("This is where you can search the user in the company's directory by entering different fields.");
		adb.setNegativeButton("OK", null);
		adb.show();
	}
}