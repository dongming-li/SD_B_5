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
if(empty($_REQUEST["email_address"])){
	die("No 'email_address' found in post call.");
}
if(empty($_REQUEST["encryption_code"])){
	die("No 'encryption_code' found in post call.");
}

// Store Input Variables
$email_address = $_REQUEST["email_address"];
$encryption_code = $_REQUEST["encryption_code"];

// Verify Input Types
if(!is_string(gettype($email_address))){
	die("'email_address' not in correct format (string).");	
}
if(!is_string(gettype($encryption_code))){
	die("'encryption_code' not in correct format (string).");	
}

// Create Insert SQL
$sql = 'INSERT INTO user_type (EMAIL_ADDRESS, ENCRYPTION_CODE) VALUES("'.$email_address.'","'.$encryption_code.'")';

// Run SQL
if($con->query($sql)){
	echo 'Successfully added the following information to the USER_TYPE table. EMAIL_ADDRESS: '.$email_address.' | ENCRYPTION_CODE: '.$encryption_code;
} else {
	die($con->error);
}
?>
