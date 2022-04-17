package com.example.leon.login;

/**
 * Created by jiahan on 12/4/17.
 */

import java.io.Serializable;


public class ActivityChatInformation {

        private String firstname = "FIRSTNAME: ";
        private String lastname = "LASTNAME: ";
        private String email = "EMAIL: ";


//        private String upc = "UPC: ";
//        private String name = "NAME: ";
//        private String quantity = "QUANTITY: ";
//        private String description = "DESCRIPTION: ";
//        private String binID = "BIN_ID: ";
//        private String manufacturerPrice = "MANUFACTURER_PRICE: ";
//        private String msrp = "MSRP: ";
//        private String locationID = "LOCATION_ID: ";

    public ActivityChatInformation(String firstname , String lastname , String email )
    {

//        public Information(String upc, String name, String quantity, String description, String binID,
//                           String manufacturerPrice, String msrp, String locationID)
//        {

            this.firstname = firstname;
            this.lastname = lastname;
            this.email = email;

//            this.upc = upc;
//            this.name = name;
//            this.quantity = quantity;
//            this.description = description;
//            this.binID = binID;
//            this.manufacturerPrice = manufacturerPrice;
//            this.msrp = msrp;
//            this.locationID = locationID;
        }



    public String getFirstname()
    {
        return firstname;
    }

    public void setFirstname(String firstname)
    {
        this.firstname = firstname;
    }
    public String getLastname()
    {
        return lastname;
    }

    public void setLastname(String lastname)
    {
        this.lastname = lastname;
    }
    public String getEmail()
    {
        return email;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }



//        public String getUpc()
//        {
//            return upc;
//        }
//
//        public void setUpc(String upc)
//        {
//            this.upc = upc;
//        }
//
//        public String getName()
//        {
//            return name;
//        }
//
//        public void setName(String name)
//        {
//            this.name = name;
//        }
//
//        public String getQuantity()
//        {
//            return quantity;
//        }
//
//        public void setQuantity(String quantity)
//        {
//            this.quantity = quantity;
//        }
//
//        public String getDescription()
//        {
//            return description;
//        }
//
//        public void setDescription(String description)
//        {
//            this.description = description;
//        }
//
//        public String getBinID()
//        {
//            return binID;
//        }
//
//        public void setBinID(String binID)
//        {
//            this.binID = binID;
//        }
//
//        public String getManufacturerPrice()
//        {
//            return manufacturerPrice;
//        }
//
//        public void setManufacturerPrice(String manufacturerPrice)
//        {
//            this.manufacturerPrice = manufacturerPrice;
//        }
//
//        public String getMsrp()
//        {
//            return msrp;
//        }
//
//        public void setMsrp(String msrp)
//        {
//            this.msrp = msrp;
//        }
//
//        public String getLocationID()
//        {
//            return locationID;
//        }
//
//        public void setLocationID(String locationID)
//        {
//            this.locationID = locationID;
//        }


//        @Override
//        public String toString()
//        {
//            return "UPC: " + this.upc + "\nName: " + this.name;
//        }


    @Override
    public String toString()
    {
        return "FirstName: " + this.firstname + "\nLastname: " + this.lastname + "\nEmail: " + this.email ;
    }

}
