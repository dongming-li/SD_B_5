<?php
// Set connection variables
$servername = "mysql.cs.iastate.edu";
$username = "dbu309sdb5";
$password = "cazAqXCV";
$schema = "db309sdb5";

// Create connection
$con = new mysqli($servername, $username, $password, $schema);

// Check connection
if($con->connect_error){
	die("Connection failed: ".$con->connect_error);
}

// Construct and run query
$sql = 'SELECT * FROM password';
if($result = $con->query($sql)){
	if($result->num_rows == 0){
		die("There are zero results");
	}
	while($row = $result->fetch_assoc()){
		$password = $row["PASSWORD"];
		echo $password;
	}
} else {
	die($con->error);
}

$con->close();
?>
