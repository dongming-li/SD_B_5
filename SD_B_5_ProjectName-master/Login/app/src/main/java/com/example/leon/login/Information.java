package com.example.leon.login;

import java.io.Serializable;

/**
 * Created by Jian Hang on 10/16/2017.
 */

public class Information
{
	private String upc = "UPC: ";
	private String name = "NAME: ";
	private String quantity = "QUANTITY: ";
	private String description = "DESCRIPTION: ";
	private String binID = "BIN ID: ";
	private String manufacturerPrice = "MANUFACTURER PRICE: ";
	private String msrp = "MSRP: ";
	private String locationID = "LOCATION ID: ";
	private String archive = "ARCHIVE: ";

	public Information(String upc, String name, String quantity, String description, String binID,
					   String manufacturerPrice, String msrp, String locationID, String archive)
	{
		this.upc = upc;
		this.name = name;
		this.quantity = quantity;
		this.description = description;
		this.binID = binID;
		this.manufacturerPrice = manufacturerPrice;
		this.msrp = msrp;
		this.locationID = locationID;
		this.archive = archive;
	}

	public String getUpc()
	{
		return upc;
	}

	public void setUpc(String upc)
	{
		this.upc = upc;
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

	public String getDescription()
	{
		return description;
	}

	public void setDescription(String description)
	{
		this.description = description;
	}

	public String getBinID()
	{
		return binID;
	}

	public void setBinID(String binID)
	{
		this.binID = binID;
	}

	public String getManufacturerPrice()
	{
		return manufacturerPrice;
	}

	public void setManufacturerPrice(String manufacturerPrice)
	{
		this.manufacturerPrice = manufacturerPrice;
	}

	public String getMsrp()
	{
		return msrp;
	}

	public void setMsrp(String msrp)
	{
		this.msrp = msrp;
	}

	public String getLocationID()
	{
		return locationID;
	}

	public void setLocationID(String locationID)
	{
		this.locationID = locationID;
	}

	@Override
	public String toString()
	{
		return "UPC: " + this.upc + "\nName: " + this.name;
	}

	public String getArchive()
	{
		return archive;
	}

	public void setArchive(String archive)
	{
		this.archive = archive;
	}
}


