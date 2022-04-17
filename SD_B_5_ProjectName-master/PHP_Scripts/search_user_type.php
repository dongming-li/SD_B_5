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

// Verify Input Types
if(!is_string(gettype($email))){
	die("'email_address' not in correct format (string).");	
}

// Create Search SQL
$sql = 'SELECT ENCRYPTION_CODE FROM user_type WHERE EMAIL_ADDRESS = "'.$email.'"';

// Run SQL
if($result = $con->query($sql)){
	if(!$result->num_rows > 0){
		die("The email provided returned zero results in search.");
	}
	$row = $result->fetch_assoc();
	$encryption_code = $row["ENCRYPTION_CODE"];
	echo $encryption_code;
} else {
	die("$con->error");
}

$con->close();
?>
