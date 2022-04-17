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

// Check And Store Variables From Post
$email = $_REQUEST["email"];

// UPDATE user SET VARIABLE=VALUE WHERE USER_ID=USER_ID

if(!empty($_REQUEST["tasks"])){
	$tasks = $_REQUEST["tasks"];
	$sql = 'UPDATE user_records SET TASKS="'.$tasks.'" WHERE EMAIL="'.$email.'"';
	if(!$con->query($sql)){
		die("$con->error");
	}
}

// Return Search Query On USER_ID
$sql = 'SELECT * FROM user_records WHERE EMAIL="'.$email.'"';
if($result = $con->query($sql)){
	if($result->num_rows > 0){
		while($row = $result->fetch_assoc()){
			echo $row["TASKS"];
		}
	} else{
		die("There is no user with the email: $email");
	}
} else{
	die("$con->error");
}
?>
