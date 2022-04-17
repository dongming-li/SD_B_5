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
} else if(empty($_REQUEST["user_id"])){
	die("Please provide the 'user_id'.");
}
$company_id = $_REQUEST["company_id"];
$user_id = $_REQUEST["user_id"];

// Create SQL DELETE Statement
$sql = 'DELETE FROM user'.$company_id.' WHERE USER_ID='.$user_id;

// Run SQL
if(!$con->query($sql)){
	die("$con->error");
} else {
	echo "User $user_id was successfully removed from the user$company_id table.";
}
?>
