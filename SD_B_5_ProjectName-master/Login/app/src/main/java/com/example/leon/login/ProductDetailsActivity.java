package com.example.leon.login;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Scanner;

public class ProductDetailsActivity extends AppCompatActivity
{
	private String product = "";
	private String upc = "";
	private String name = "";
	private String quantity = "";
	private String description = "";
	private String binID = "";
	private String manufacturerPrice = "";
	private String msrp = "";
	private String locationID = "";
	private int position = 0;
	private TextView productDetails;


	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_product_details);
		productDetails = (TextView) findViewById(R.id.ProductInfo);
		quantity = RecyclerViewAdapter.ProductListHolder.quantity;
		description = RecyclerViewAdapter.ProductListHolder.description;
		position = getIntent().getIntExtra("position", 0);

		addInformation();
		productDetails.setText(product);
	}

	protected void addInformation()
	{
		upc = RecyclerViewAdapter.productList.get(position).getUpc() + "\n";
		name = RecyclerViewAdapter.productList.get(position).getName().split(":")[1] + "\n";
		quantity = RecyclerViewAdapter.productList.get(position).getQuantity().split(":")[1] + "\n";
		description = RecyclerViewAdapter.productList.get(position).getDescription().split(":")[1] + "\n";
		binID = RecyclerViewAdapter.productList.get(position).getBinID().split(":")[1] + "\n";
		manufacturerPrice = RecyclerViewAdapter.productList.get(position).getManufacturerPrice().split(":")[1] + "\n";
		msrp = RecyclerViewAdapter.productList.get(position).getMsrp().split(":")[1] + "\n";
		locationID = RecyclerViewAdapter.productList.get(position).getLocationID().split(":")[1] + "\n";

		product = upc + name + quantity + description + binID + manufacturerPrice + msrp + locationID;
	}
}
