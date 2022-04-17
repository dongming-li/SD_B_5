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

if(empty($_REQUEST["company_id"])){
	die("Please provide the 'company_id");
}
$company_id = $_REQUEST["company_id"];

if(empty($_REQUEST["location_id"])){
	$sql = 'SELECT MAX(LOCATION_ID) AS LOCATION_ID FROM store'.$company_id;
	if($result = $conn->query($sql)){
		if($result->num_rows > 0){
			$row = $result->fetch_assoc();
			$location_id = ((int) $row["LOCATION_ID"]) + 1;
		} else {
			$location_id = 2;
		}
	} else {
		die("$conn->error");
	}
} else {
	$location_id = $_REQUEST["location_id"];
}

if(!empty($_REQUEST["lat"]) && !empty($_REQUEST["long"]) && !empty($_REQUEST["manager_id"]) && !empty($_REQUEST["email"]) && !empty($_REQUEST["phone"]) && !empty($_REQUEST["address"]) && !empty($_REQUEST["state"]) && !empty($_REQUEST["city"]) && !empty($_REQUEST["zip_code"])){
	$latitude = $_REQUEST["lat"];
	$longitude = $_REQUEST["long"];
	$manager_id = $_REQUEST["manager_id"];
	$email = $_REQUEST["email"];
	$phone_number = $_REQUEST["phone"];
	$address = $_REQUEST["address"];
	$state = $_REQUEST["state"];
	$city = $_REQUEST["city"];
	$zipcode = $_REQUEST["zip_code"];
	if(!empty($_REQUEST["warehouse"])){
		$warehouse = $_REQUEST["warehouse"];
		$description = "Warehouse";
	} else {
		$warehouse = 0;
		$description = "Store";
	}
	$sql = 'INSERT INTO store'.$company_id.' (LOCATION_ID, LATITUDE, LONGITUDE, MANAGER_ID, PHONE_NUMBER, COMPANY_ID, EMAIL, ADDRESS, STATE, CITY, ZIPCODE, DESCRIPTION, WAREHOUSE) VALUES('.$location_id.',"'.$latitude.'","'.$longitude.'",'.$manager_id.','.$phone_number.','.$company_id.',"'.$email.'","'.$address.'","'.$state.'","'.$city.'",'.$zipcode.',"'.$description.'",'.$warehouse.')';
} else {
	die("Unable to obtain input.");
}

if($conn->query($sql)){
	echo $location_id;
} else {
	("$conn->error");
}

$sql = 'CREATE TABLE product'.$company_id.'_'.$location_id.'(UPC INT NOT NULL, PRODUCT_NAME VARCHAR(50) NOT NULL, QUANTITY INT NOT NULL, DESCRIPTION VARCHAR(50), BIN_ID INT NOT NULL, MANUFACTURER_PRICE VARCHAR(10), MSRP VARCHAR(10), LOCATION_ID INT NOT NULL, ARCHIVE INT NOT NULL, PRIMARY KEY (UPC))';

if(!$conn->query($sql)){
	die("$conn->error");
}
$conn->close();
?>
