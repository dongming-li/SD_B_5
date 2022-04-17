package com.example.leon.login;

/**
 * Created by Jian Hang on 11/8/2017.
 */

public class DirectoryInformation
{
	private String userID;
	private String locationID;
	private String userType;
	private String firstName;
	private String lastName;
	private String email;
	private String phoneNumber;
	private String address;
	private String state;
	private String city;
	private String zipcode;

	public DirectoryInformation(String userID, String locationID, String userType, String firstName, String lastName,
								String email, String phoneNumber, String address, String state, String city, String zipcode)
	{
		this.userID = userID;
		this.locationID = locationID;
		this.userType = userType;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.phoneNumber = phoneNumber;
		this.address = address;
		this.state = state;
		this.city = city;
		this.zipcode = zipcode;
	}

	public String getUserID()
	{
		return userID;
	}

	public void setUserID(String userID)
	{
		this.userID = userID;
	}

	public String getLocationID()
	{
		return locationID;
	}

	public void setLocationID(String locationID)
	{
		this.locationID = locationID;
	}

	public String getUserType()
	{
		return userType;
	}

	public void setUserType(String userType)
	{
		this.userType = userType;
	}

	public String getFirstName()
	{
		return firstName;
	}

	public void setFirstName(String firstName)
	{
		this.firstName = firstName;
	}

	public String getLastName()
	{
		return lastName;
	}

	public void setLastName(String lastName)
	{
		this.lastName = lastName;
	}

	public String getEmail()
	{
		return email;
	}

	public void setEmail(String email)
	{
		this.email = email;
	}

	public String getPhoneNumber()
	{
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber)
	{
		this.phoneNumber = phoneNumber;
	}

	public String getAddress()
	{
		return address;
	}

	public void setAddress(String address)
	{
		this.address = address;
	}

	public String getState()
	{
		return state;
	}

	public void setState(String state)
	{
		this.state = state;
	}

	public String getCity()
	{
		return city;
	}

	public void setCity(String city)
	{
		this.city = city;
	}

	public String getZipcode()
	{
		return zipcode;
	}

	public void setZipcode(String zipcode)
	{
		this.zipcode = zipcode;
	}
}
