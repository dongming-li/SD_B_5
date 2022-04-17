package com.example.leon.login;

/**
 * Created by Jian Hang on 12/4/2017.
 */

public class OrderInformation
{
	private String orderID = "";
	private String upc = "";
	private String status = "";
	private String name = "";
	private String quantity = "";
	private String locationID = "";
	private String destinationID = "";
	private String deliveryDate = "";

	public OrderInformation(String orderID, String upc, String status, String name, String quantity,
							String locationID, String destinationID, String deliveryDate)
	{
		this.orderID = orderID;
		this.upc = upc;
		this.status = status;
		this.name = name;
		this.quantity = quantity;
		this.locationID = locationID;
		this.destinationID = destinationID;
		this.deliveryDate = deliveryDate;
	}

	public String getOrderID()
	{
		return orderID;
	}

	public void setOrderID(String orderID)
	{
		this.orderID = orderID;
	}

	public String getUpc()
	{
		return upc;
	}

	public void setUpc(String upc)
	{
		this.upc = upc;
	}

	public String getStatus()
	{
		return status;
	}

	public void setStatus(String status)
	{
		this.status = status;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getQuantity()
	{
		return quantity;
	}

	public void setQuantity(String quantity)
	{
		this.quantity = quantity;
	}

	public String getLocationID()
	{
		return locationID;
	}

	public void setLocationID(String locationID)
	{
		this.locationID = locationID;
	}

	public String getDestinationID()
	{
		return destinationID;
	}

	public void setDestinationID(String destinationID)
	{
		this.destinationID = destinationID;
	}

	public String getDeliveryDate()
	{
		return deliveryDate;
	}

	public void setDeliveryDate(String deliveryDate)
	{
		this.deliveryDate = deliveryDate;
	}
}
