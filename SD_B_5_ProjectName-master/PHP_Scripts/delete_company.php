<?php
$servername = "mysql.cs.iastate.edu";
$username = "dbu309sdb5";
$password = "cazAqXCV";
$schema = "db309sdb5";

// Create Connection
$con = new mysqli($servername, $username, $password, $schema);

// Check Connection
if ($con->connect_error) {
	die("Connection failed: " . $con->connect_error);
}


// Check for Company ID
if(empty($_REQUEST["company_id"])){
	die("Please provide the 'company_id'");
}
$company_id = $_REQUEST["company_id"];

// Delete Each of the Tables
$sql = 'DROP TABLE product'.$company_id;
if(!$con->query($sql)){
	die($con->error);
}

$sql = 'DROP TABLE user'.$company_id;
if(!$con->query($sql)){
	die($con->error);
}

$sql = 'DROP TABLE store'.$company_id;
if(!$con->query($sql)){
	die($con->error);
}

$sql = 'DROP TABLE order'.$company_id;
if(!$con->query($sql)){
	die($con->error);
}

$sql = 'DELETE FROM company WHERE COMPANY_ID='.$company_id;
if(!$con->query($sql)){
	die($con->error);
}

echo "Company $company_id has been deleted.";

$con->close();
?>
