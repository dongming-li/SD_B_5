<?php
$servername = "mysql.cs.iastate.edu";
$username = "dbu309sdb5";
$password = "cazAqXCV";
$schema = "db309sdb5";

// Create Connection
$con = new mysqli($servername, $username, $password, $schema);

// Check Connection
if($con->connect_error){
	die("Connection failed:".$con->connect_error);
}

// Check for variables in post
if(empty($_REQUEST["company_id"])){
	die("Please provide the 'company_id'.");
} else if(empty($_REQUEST["location_id"])){
	die("Please provide the 'location_id'.");
}
$company_id = $_REQUEST["company_id"];
$location_id = $_REQUEST["location_id"];

// Create SQL DELETE Statement
$sql = 'DELETE FROM store'.$company_id.' WHERE LOCATION_ID='.$location_id;

// Run SQL
if(!$con->query($sql)){
	die("$con->error");
} else {
	echo "Store $location_id was successfully removed from the locaiton$company_id table.";
}
?>
