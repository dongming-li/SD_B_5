package com.example.leon.login;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.List;

/**
 * Created by Jian Hang on 10/25/2017.
 */

public class DirectoryRecyclerViewAdapter extends RecyclerView.Adapter<DirectoryRecyclerViewAdapter.DirectoryListHolder>
{
	private List<DirectoryInformation> directoryList;
	private final String DATA_URL_DELETE_USER = "http://proj-309-sd-b-5.cs.iastate.edu/database/remove_directory.php?company_id=" + MainActivity.company_id + "&user_id=";
	private String userId;

	public class DirectoryListHolder extends RecyclerView.ViewHolder
	{
		CardView cardView;
		TextView userId;
		TextView locationId;
		ImageView overflow;
		View view;

		DirectoryListHolder(View itemView)
		{
			super(itemView);
			cardView = (CardView) itemView.findViewById(R.id.cardView);
			userId = (TextView) itemView.findViewById(R.id.textViewUserId);
			locationId = (TextView) itemView.findViewById(R.id.textViewlocationId);
			overflow = (ImageView) itemView.findViewById(R.id.overflow);
			view = itemView;
		}
	}

	public DirectoryRecyclerViewAdapter(List<DirectoryInformation> directoryList)
	{
		this.directoryList = directoryList;
	}

	@Override
	public DirectoryListHolder onCreateViewHolder(ViewGroup viewGroup, int pos)
	{
		View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_directory, viewGroup, false);
		DirectoryListHolder directoryListHolder = new DirectoryListHolder(view);
		return directoryListHolder;
	}

	@Override
	public void onBindViewHolder(final DirectoryListHolder productListHolder, int pos)
	{
		DirectoryInformation directory = directoryList.get(pos);
		productListHolder.userId.setText(directory.getUserID());
		userId = directory.getUserID();
		userId = userId.split(":")[1];
		productListHolder.locationId.setText(directory.getLocationID());
		productListHolder.overflow.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View view)
			{
				showPopupMenu(productListHolder.overflow);
			}
		});
	}

	private void showPopupMenu(final View view)
	{
		PopupMenu popup = new PopupMenu(view.getContext(), view);
		MenuInflater menuInflater = popup.getMenuInflater();
		menuInflater.inflate(R.menu.menu_directory, popup.getMenu());
		popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener()
		{
			@Override
			public boolean onMenuItemClick(MenuItem menuItem)
			{
				switch (menuItem.getItemId())
				{
					case R.id.action_delete_user:
						// Show delete user alert dialog
						deleteUserAlertDialog(view);
						return true;
					default:
				}
				return false;
			}
		});
		popup.show();
	}

	@Override
	public int getItemCount()
	{
		return directoryList.size();
	}

	private void deleteUserAlertDialog(final View view)
	{
		final Context context = view.getContext();

		AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
		builder.setMessage("Do you want to delete this user? ");

		builder.setPositiveButton("Yes", new DialogInterface.OnClickListener()
		{
			@Override
			public void onClick(DialogInterface dialogInterface, int i)
			{
				deleteUser(view);
			}
		});
		builder.setNegativeButton("No", null);

		AlertDialog alertDialog = builder.create();
		alertDialog.show();
	}

	private void deleteUser(final View view)
	{
		String url = DATA_URL_DELETE_USER + userId;

		RequestQueue requestQueue = Volley.newRequestQueue(view.getContext());
		StringRequest postRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>()
		{
			@Override
			public void onResponse(String response)
			{
				Toast.makeText(view.getContext(), "User is successfully deleted. ", Toast.LENGTH_LONG).show();
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
