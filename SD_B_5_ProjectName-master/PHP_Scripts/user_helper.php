<?php
// Set Connection Variables
$servername = "mysql.cs.iastate.edu";
$username = "dbu309sdb5";
$password = "cazAqXCV";
$schema = "db309sdb5";
 
// Create Connection
$con = new mysqli($servername, $username, $password, $schema);

// Check Connection
if($con->connect_error){
	die("Connection failed: ".$con->connect_error);	
}

// Check Input Variables
if(empty($_REQUEST["email"])){
	die("No 'email_address' found in post call.");
}

// Store Input Variables
$email = $_REQUEST["email"];
$company_id = $_REQUEST["company_id"];
$first_name = $_REQUEST["first_name"];
$last_name = $_REQUEST["last_name"];
$location_id = $_REQUEST["location_id"];
$user_type = $_REQUEST["user_type"];

// Verify Input Types
if(!is_string(gettype($email))){
	die("'email_address' not in correct format (string).");	
}

// Create Search SQL
$sql = 'INSERT INTO user_records (EMAIL, COMPANY_ID, LOCATION_ID, USER_TYPE, FIRST_NAME, LAST_NAME) VALUES ("'.$email.'",'.$company_id.','.$location_id.','.$user_type.',"'.$first_name.'","'.$last_name.'")';

// Run SQL
if($con->query($sql)){
} else {
	die("$con->error");
}

$con->close();
?>
