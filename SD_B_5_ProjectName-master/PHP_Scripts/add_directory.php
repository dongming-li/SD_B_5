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

if(empty($_REQUEST["user_id"])){
	$sql = 'SELECT MAX(USER_ID) AS USER_ID FROM user'.$company_id;
	if($result = $conn->query($sql)){
		if($result->num_rows > 0){
			$row = $result->fetch_assoc();
			$user_id = ((int) $row["USER_ID"]) + 1;
		} else {
			$user_id = 2;
		}
	} else {
		die("$conn->error");
	}
} else {
	$user_id = $_REQUEST["user_id"];
}

if(!empty($_REQUEST["last_name"]) && !empty($_REQUEST["first_name"]) && !empty($_REQUEST["location_id"]) && !empty($_REQUEST["user_type"]) && !empty($_REQUEST["email"]) && !empty($_REQUEST["phone_number"]) && !empty($_REQUEST["address"]) && !empty($_REQUEST["state"]) && !empty($_REQUEST["city"]) && !empty($_REQUEST["zip_code"])){
	$last_name = $_REQUEST["last_name"];
	$first_name = $_REQUEST["first_name"];
	$location_id = $_REQUEST["location_id"];
	$user_type = $_REQUEST["user_type"];
	$email = $_REQUEST["email"];
	$phone_number = $_REQUEST["phone_number"];
	$address = $_REQUEST["address"];
	$state = $_REQUEST["state"];
	$city = $_REQUEST["city"];
	$zipcode = $_REQUEST["zip_code"];
	$lat = $_REQUEST["lat"];
	$long = $_REQUEST["long"];
	//if(empty($_REQUEST["tasks"])){
	//	$tasks = 0;
	//} else {
	//	$tasks = $_REQUEST["tasks"];
	//}
	$sql = 'INSERT INTO user'.$company_id.' (USER_ID, LOCATION_ID, USER_TYPE, FIRST_NAME, LAST_NAME, EMAIL, PHONE_NUMBER, ADDRESS, STATE, CITY, ZIPCODE, LATITUDE, LONGITUDE, TASKS) VALUES('.$user_id.','.$location_id.','.$user_type.',"'.$first_name.'","'.$last_name.'","'.$email.'","'.$phone_number.'","'.$address.'","'.$state.'","'.$city.'",'.$zipcode.','.$lat.','.$long.',"")';
} else {
	die("Unable to obtain input.");
}

if($conn->query($sql)){
	echo $user_id;
} else {
	("$conn->error");
}

$conn->close();
?>
