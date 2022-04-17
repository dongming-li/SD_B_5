<?php
$servername = "mysql.cs.iastate.edu";
$username = "dbu309sdb5";
$password = "cazAqXCV";
$schema = "db309sdb5";
     
// Create Connection 
$conn = new mysqli($servername, $username, $password, $schema);

// Check connection
if ($conn->connect_error) {
	die("Connection failed: " . $conn->connect_error);
}

// Create new entry for the new company

if(empty($_REQUEST["company_name"])){
	die("Please provide your new company name by passing 'company_name'");
}
if(empty($_REQUEST["state"])){
	die("Please provide the state for your first location. 'state'");
}
if(empty($_REQUEST["city"])){
	die("Please provide the city for your first location. 'city'");
}
if(empty($_REQUEST["street_address"])){
	die("Please provide the street for your first location. 'address'");
}
if(empty($_REQUEST["zip_code"])){
	die("Please provide the zipcode for your first location. 'zipcode'");
}
if(empty($_REQUEST["phone"])){
	die("Please provide the phone number for your first location. 'phone'");
}
if(empty($_REQUEST["email"])){
	die("Please provide the email. 'email'");
}
if(empty($_REQUEST["lat"])){
	$lat = 1.1;
}else{
	$lat = $_REQUEST["lat"];
}
if(empty($_REQUEST["long"])){
	$long = 1.1;
} else {
	$long = $_REQUEST["long"];
}
$company_name = $_REQUEST["company_name"];

// IF SHIT BREAKS REMOVE THIS BLOCK ////////////////
$sql = 'SELECT * FROM company WHERE COMPANY_NAME="'.$company_name.'"';
if($result = $conn->query($sql)){
	if($result->num_rows>0){
		die("Company name already exists.");
	}	
} else {
	die("$conn->error");
}
/////////////////////////////////////////////////////////

$sql = 'SELECT MAX(COMPANY_ID) AS COMPANY_ID FROM company';
if($result = $conn->query($sql)){
	if($result->num_rows > 0){
		$row = $result->fetch_assoc();
		$last_cid = (int) $row["COMPANY_ID"];
		$company_id = (int) ($last_cid + 1);
		$sql = 'INSERT INTO company (COMPANY_ID, COMPANY_NAME) VALUES('.$company_id.',"'.$company_name.'")';
		if(!$conn->query($sql)){
			die("$conn->error");
		}
	} else {
		die("$conn->error");
	}
} else {
	die("$conn->error");
}

// Create all company tables

$sql = 'CREATE TABLE store'.$company_id.'(LOCATION_ID INT NOT NULL, LATITUDE DOUBLE NOT NULL, LONGITUDE DOUBLE NOT NULL, MANAGER_ID INT NOT NULL, PHONE_NUMBER BIGINT(15) NOT NULL, COMPANY_ID INT NOT NULL, EMAIL VARCHAR(50) NOT NULL, ADDRESS VARCHAR(50) NOT NULL, STATE VARCHAR(50) NOT NULL, CITY VARCHAR(50) NOT NULL, ZIPCODE INT NOT NULL, DESCRIPTION VARCHAR(100) NOT NULL, WAREHOUSE INT NOT NULL, PRIMARY KEY (LOCATION_ID))';

if(!$conn->query($sql)){
	die("$conn->error");
}

$sql = 'CREATE TABLE product'.$company_id.'_1 (UPC INT NOT NULL, PRODUCT_NAME VARCHAR(50) NOT NULL, QUANTITY INT NOT NULL, DESCRIPTION VARCHAR(50), BIN_ID INT NOT NULL, MANUFACTURER_PRICE VARCHAR(10), MSRP VARCHAR(10), LOCATION_ID INT NOT NULL, ARCHIVE INT NOT NULL, PRIMARY KEY (UPC))';

if(!$conn->query($sql)){
	die("$conn->error");
}

$sql = 'CREATE TABLE user'.$company_id.'(USER_ID INT NOT NULL, LOCATION_ID INT NOT NULL, USER_TYPE INT NOT NULL, FIRST_NAME VARCHAR(45) NOT NULL, LAST_NAME VARCHAR(45) NOT NULL, EMAIL VARCHAR(45) NOT NULL, PHONE_NUMBER BIGINT(15) NOT NULL, ADDRESS VARCHAR(50) NOT NULL, STATE VARCHAR(50) NOT NULL, CITY VARCHAR(50) NOT NULL, ZIPCODE INT NOT NULL, LATITUDE DOUBLE NOT NULL, LONGITUDE DOUBLE NOT NULL, TASKS VARCHAR(999), PRIMARY KEY (USER_ID))';

if(!$conn->query($sql)){
	die("$conn->error");
}

$sql = 'CREATE TABLE order'.$company_id.'(ORDER_ID INT NOT NULL, UPC INT NOT NULL, STATUS INT NOT NULL, PRODUCT_NAME VARCHAR(50) NOT NULL, QUANTITY INT NOT NULL, LOCATION_ID INT NOT NULL, DESTINATION_ID INT NOT NULL, EXPECTED_DELIVERY_DATE DATE NOT NULL, PRIMARY KEY (ORDER_ID))';

if(!$conn->query($sql)){
	die("$conn->error");
}
$phone_number = $_REQUEST["phone"];

$state = $_REQUEST["state"];
$city = $_REQUEST["city"];
$address = $_REQUEST["street_address"];
$zipcode = $_REQUEST["zip_code"];
$email = $_REQUEST["email"];
// INSERT FIRST LOCATION TO STORE TABLE FOR HEADQUARTERS

$sql = 'INSERT INTO store'.$company_id.'(LOCATION_ID, LATITUDE, LONGITUDE, MANAGER_ID, PHONE_NUMBER, COMPANY_ID, EMAIL, ADDRESS, STATE, CITY, ZIPCODE, DESCRIPTION, WAREHOUSE) VALUES(1,'.$lat.','.$long.',1,'.$phone_number.','.$company_id.',"'.$email.'","'.$address.'","'.$state.'","'.$city.'",'.$zipcode.',"Headquarters for company.",0)';

if(!$conn->query($sql)){
	die("$conn->error");
}


// RETURN APPROPRIATE INFORMATION
echo $company_id;
$conn->close();
?>
