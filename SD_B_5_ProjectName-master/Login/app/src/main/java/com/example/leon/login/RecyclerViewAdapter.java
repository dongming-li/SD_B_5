package com.example.leon.login;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.leon.login.MainActivity;

import org.w3c.dom.Text;

import java.util.List;

/**
 * Created by Jian Hang on 10/25/2017.
 */

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ProductListHolder>
{
	static List<Information> productList;
	private TextView productDetails;
	private final String DATA_URL_REMOVE = "http://proj-309-sd-b-5.cs.iastate.edu/database/remove.php?company_id=" + MainActivity.company_id + "&upc=";
	private final String DATA_URL_CHANGE_ARCHIVE = "http://proj-309-sd-b-5.cs.iastate.edu/database/update.php?company_id=" + MainActivity.company_id + "&upc=";
	private String upc = "";
	private String locationID = "";
	private String archive = "";
	private EditText editTextQuantity;

	public static class ProductListHolder extends RecyclerView.ViewHolder
	{
		CardView cardView;
		static TextView upc;
		static TextView name;
		int position;
		ImageView overflow;
		static String description;
		static String binID;
		static String manufacturerPrice;
		static String msrp;
		static String locationID;
		static String quantity;

		ProductListHolder(final View itemView)
		{
			super(itemView);
			cardView = (CardView) itemView.findViewById(R.id.cardView);
			upc = (TextView) itemView.findViewById(R.id.upc);
			name = (TextView) itemView.findViewById(R.id.name);
			overflow = (ImageView) itemView.findViewById(R.id.overflow);
		}
	}

	public RecyclerViewAdapter(List<Information> productList)
	{
		this.productList = productList;
	}

	@Override
	public ProductListHolder onCreateViewHolder(ViewGroup viewGroup, int pos)
	{
		View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_product, viewGroup, false);
		ProductListHolder productListHolder = new ProductListHolder(view);
		return productListHolder;
	}

	@Override
	public void onBindViewHolder(final ProductListHolder productListHolder, final int pos)
	{
		final Information product = productList.get(pos);
		productListHolder.position = productListHolder.getAdapterPosition();
		productListHolder.upc.setText(product.getUpc());
		productListHolder.name.setText(product.getName());
		productListHolder.quantity = product.getQuantity();
		productListHolder.description = product.getDescription();
		productListHolder.binID = product.getBinID();
		productListHolder.manufacturerPrice = product.getManufacturerPrice();
		productListHolder.msrp = product.getMsrp();
		productListHolder.locationID = product.getLocationID();
		productListHolder.overflow.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View view)
			{
				showPopupMenu(productListHolder.overflow, productListHolder.position);
			}
		});
	}

	private void showPopupMenu(final View view, final int pos)
	{
		PopupMenu popup = new PopupMenu(view.getContext(), view);
		MenuInflater menuInflater = popup.getMenuInflater();
		menuInflater.inflate(R.menu.menu_product, popup.getMenu());
		popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener()
		{
			@Override
			public boolean onMenuItemClick(MenuItem menuItem)
			{
				switch (menuItem.getItemId())
				{
					case R.id.action_remove_product:
						removeProduct(view, pos);
						return true;
					case R.id.action_archive_product:
						changeArchive(view, pos);
						return true;
					case R.id.action_details_product:
						showDetails(view, pos);
						return true;
					default:
				}
				return false;
			}
		});
		popup.show();
	}

	private void changeArchive(final View view, final int pos)
	{
		upc = productList.get(pos).getUpc().split(":")[1];
		locationID = productList.get(pos).getLocationID().split(":")[1];
		archive = productList.get(pos).getArchive().split(":")[1];
		String url = "";

		if (archive.equals("1"))
			url = DATA_URL_CHANGE_ARCHIVE + upc + "&archive=2";
		else
			url = DATA_URL_CHANGE_ARCHIVE + upc + "&archive=1";

		RequestQueue requestQueue = Volley.newRequestQueue(view.getContext());
		StringRequest postRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>()
		{
			@Override
			public void onResponse(String response)
			{
				if (archive.equals("1"))
					Toast.makeText(view.getContext(), "Product is unarchived ", Toast.LENGTH_LONG).show();
				else
					Toast.makeText(view.getContext(), "Product is archived ", Toast.LENGTH_LONG).show();
			}
		},
				new Response.ErrorListener()
				{
					@Override
					public void onErrorResponse(VolleyError error)
					{
						Toast.makeText(view.getContext(), error.getMessage(), Toast.LENGTH_LONG).show();
					}
				});
		requestQueue.add(postRequest);
	}

	private void showDetails(final View view, final int pos)
	{
		String details = "";
		String upc = "";
		String name = "";
		String quantity = "";
		String description = "";
		String binID = "";
		String manufacturerPrice = "";
		String msrp = "";
		String locationID = "";
		String archive = "";

		LayoutInflater layoutInflater = LayoutInflater.from(view.getContext());
		final View view1 = layoutInflater.inflate(R.layout.activity_product_details, null);
		AlertDialog.Builder builder = new AlertDialog.Builder(view1.getContext());
		builder.setTitle("Product Details");
		productDetails = (TextView) view1.findViewById(R.id.ProductInfo);

		upc = RecyclerViewAdapter.productList.get(pos).getUpc() + "\n";
		name = RecyclerViewAdapter.productList.get(pos).getName() + "\n";
		quantity = RecyclerViewAdapter.productList.get(pos).getQuantity() + "\n";
		description = RecyclerViewAdapter.productList.get(pos).getDescription() + "\n";
		binID = RecyclerViewAdapter.productList.get(pos).getBinID() + "\n";
		manufacturerPrice = RecyclerViewAdapter.productList.get(pos).getManufacturerPrice() + "\n";
		msrp = RecyclerViewAdapter.productList.get(pos).getMsrp() + "\n";
		locationID = RecyclerViewAdapter.productList.get(pos).getLocationID() + "\n";
		archive = RecyclerViewAdapter.productList.get(pos).getArchive();

		details = upc + name + quantity + description + binID + manufacturerPrice + msrp + locationID + archive;

		productDetails.setText(details);

		AlertDialog alertDialog = builder.create();
		alertDialog.setView(view1);
		alertDialog.show();
	}

	@Override
	public int getItemCount()
	{
		return productList.size();
	}

	private void removeProduct(final View view, final int pos)
	{
		LayoutInflater layoutInflater = LayoutInflater.from(view.getContext());
		final View view1 = layoutInflater.inflate(R.layout.edit_product_dialog, null);
		AlertDialog.Builder builder = new AlertDialog.Builder(view1.getContext());

		builder.setPositiveButton("Reduce Quantity", new DialogInterface.OnClickListener()
		{
			@Override
			public void onClick(DialogInterface dialogInterface, int i)
			{
				reduceQuantity(view1, pos);
			}
		});
		builder.setNeutralButton("Remove Product", new DialogInterface.OnClickListener()
		{
			@Override
			public void onClick(DialogInterface dialogInterface, int i)
			{
				removeFromCatalog(view1, pos);
			}
		});
		builder.setNegativeButton("Cancel", null);

		AlertDialog alertDialog = builder.create();
		alertDialog.setView(view1);
		alertDialog.show();
	}

	private void reduceQuantity(final View view, final int pos)
	{
		editTextQuantity = view.findViewById(R.id.editTextQuantity);
		final String quantity = editTextQuantity.getText().toString();

		if (quantity.isEmpty())
		{
			Toast.makeText(view.getContext(), "Please enter a quantity", Toast.LENGTH_LONG).show();
			return;
		}
		else
		{
			upc = productList.get(pos).getUpc().split(":")[1];

			String url = DATA_URL_REMOVE + upc + "&delete=2" + "&quantity=" + quantity;

			RequestQueue requestQueue = Volley.newRequestQueue(view.getContext());
			StringRequest postRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>()
			{
				@Override
				public void onResponse(String response)
				{
					int compareQuantity = Integer.parseInt(productList.get(pos).getQuantity().split(":")[1]);
					int enteredQuantity = Integer.parseInt(quantity);

					if (enteredQuantity > compareQuantity)
						Toast.makeText(view.getContext(), "Product quantity is reduced by " + compareQuantity, Toast.LENGTH_LONG).show();
					else
						Toast.makeText(view.getContext(), "Product quantity is reduced by " + quantity, Toast.LENGTH_LONG).show();
				}
			},
					new Response.ErrorListener()
					{
						@Override
						public void onErrorResponse(VolleyError error)
						{
							Toast.makeText(view.getContext(), error.getMessage(), Toast.LENGTH_LONG).show();
						}
					});
			requestQueue.add(postRequest);
		}
	}

	private void removeFromCatalog(final View view, final int pos)
	{
		upc = productList.get(pos).getUpc().split(":")[1];

		String url = DATA_URL_REMOVE + upc + "&delete=1";

		RequestQueue requestQueue = Volley.newRequestQueue(view.getContext());
		StringRequest postRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>()
		{
			@Override
			public void onResponse(String response)
			{
				Toast.makeText(view.getContext(), "Product is removed from catalog. ", Toast.LENGTH_LONG).show();
			}
		},
				new Response.ErrorListener()
				{
					@Override
					public void onErrorResponse(VolleyError error)
					{
						Toast.makeText(view.getContext(), error.getMessage(), Toast.LENGTH_LONG).show();
					}
				});
		requestQueue.add(postRequest);
	}
}
