package com.example.leon.login;

import android.app.AlertDialog;
import android.content.Context;
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
 * Created by Jian Hang on 12/4/2017.
 */

public class OrderRecyclerViewAdapter extends RecyclerView.Adapter<OrderRecyclerViewAdapter.OrderListHolder>
{
	static List<OrderInformation> orderList;
	private TextView orderDetails;
	private String orderID = "";
	private String status = "";
	private final String DATA_CHANGE_STATUS = "http://proj-309-sd-b-5.cs.iastate.edu/database/update_order.php?" +
			"company_id=" + MainActivity.company_id + "&order_id=";


	public static class OrderListHolder extends RecyclerView.ViewHolder
	{
		CardView cardView;
		static TextView orderID;
		int position;
		ImageView overflow;
		static String upc;
		static String name;
		static String quantity;
		static String locationID;
		static String destinationID;
		static String deliveryDate;
		static String status;

		OrderListHolder(final View itemView)
		{
			super(itemView);
			cardView = (CardView) itemView.findViewById(R.id.cardView);
			orderID = (TextView) itemView.findViewById(R.id.orderId);
			overflow = (ImageView) itemView.findViewById(R.id.overflow);
		}
	}
	public OrderRecyclerViewAdapter(List<OrderInformation> orderList)
	{
		this.orderList = orderList;
	}

	@Override
	public OrderListHolder onCreateViewHolder(ViewGroup viewGroup, int pos)
	{
		View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.order_item, viewGroup, false);
		OrderListHolder productListHolder = new OrderListHolder(view);
		return productListHolder;
	}

	@Override
	public void onBindViewHolder(final OrderListHolder orderListHolder, final int pos)
	{
		final OrderInformation order = orderList.get(pos);
		orderListHolder.position = orderListHolder.getAdapterPosition();
		orderListHolder.orderID.setText(order.getOrderID());
		orderListHolder.upc = order.getUpc();
		orderListHolder.name = order.getName();
		orderListHolder.quantity = order.getQuantity();
		orderListHolder.locationID = order.getLocationID();
		orderListHolder.destinationID = order.getDestinationID();
		orderListHolder.deliveryDate = order.getDeliveryDate();
		orderListHolder.status = order.getStatus();
		orderListHolder.overflow.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View view)
			{
				showPopupMenu(orderListHolder.overflow, orderListHolder.position);
			}
		});
	}

	private void showPopupMenu(final View view, final int pos)
	{
		PopupMenu popup = new PopupMenu(view.getContext(), view);
		MenuInflater menuInflater = popup.getMenuInflater();
		menuInflater.inflate(R.menu.menu_order, popup.getMenu());
		popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener()
		{
			@Override
			public boolean onMenuItemClick(MenuItem menuItem)
			{
				switch (menuItem.getItemId())
				{
					case R.id.action_details_order:
						showDetails(view, pos);
						return true;
					case R.id.action_change_status:
						changeStatus(view, pos);
					default:
				}
				return false;
			}
		});
		popup.show();
	}

	private void changeStatus(final View view, final int pos)
	{
		orderID = orderList.get(pos).getOrderID().split(":")[1];
		status = orderList.get(pos).getStatus().split(":")[1];
		String url = "";

		if (status.equals("1"))
			url = DATA_CHANGE_STATUS + orderID + "&status=2";
		else
			url = DATA_CHANGE_STATUS + orderID + "&status=1";

		RequestQueue requestQueue = Volley.newRequestQueue(view.getContext());
		StringRequest postRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>()
		{
			@Override
			public void onResponse(String response)
			{
				if (status.equals("1"))
					Toast.makeText(view.getContext(), "Order is changed to Completed", Toast.LENGTH_LONG).show();
				else
					Toast.makeText(view.getContext(), "Order is changed to Pending", Toast.LENGTH_LONG).show();
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
		String orderID = "";
		String upc = "";
		String name = "";
		String quantity = "";
		String locationID = "";
		String destinationID = "";
		String deliveryDate = "";
		String status = "";

		final Context context = view.getContext();
		LayoutInflater layoutInflater = LayoutInflater.from(view.getContext());
		final View view1 = layoutInflater.inflate(R.layout.order_details, null);
		AlertDialog.Builder builder = new AlertDialog.Builder(view1.getContext());
		builder.setTitle("Order Details");
		orderDetails = (TextView) view1.findViewById(R.id.OrderInfo);

		orderID = OrderRecyclerViewAdapter.orderList.get(pos).getOrderID() + "\n";
		upc = OrderRecyclerViewAdapter.orderList.get(pos).getUpc() + "\n";
		name = OrderRecyclerViewAdapter.orderList.get(pos).getName() + "\n";
		quantity = OrderRecyclerViewAdapter.orderList.get(pos).getQuantity() + "\n";
		locationID = OrderRecyclerViewAdapter.orderList.get(pos).getLocationID() + "\n";
		destinationID = OrderRecyclerViewAdapter.orderList.get(pos).getDestinationID() + "\n";
		deliveryDate = OrderRecyclerViewAdapter.orderList.get(pos).getDeliveryDate() + "\n";
		status = OrderRecyclerViewAdapter.orderList.get(pos).getStatus() + "\n";

		details = orderID + upc + name + quantity + locationID + destinationID + deliveryDate + status;

		orderDetails.setText(details);

		AlertDialog alertDialog = builder.create();
		alertDialog.setView(view1);
		alertDialog.show();
	}

	@Override
	public int getItemCount()
	{
		return orderList.size();
	}
}
