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

// Take in post variables
$order_id = $_REQUEST["email"];

// Construct SQL query
$sql = 'SELECT * FROM user_records WHERE EMAIL = "'.$user_id.'"';

// Run query to return search results
if($result = $conn->query($sql)){
	if($result->num_rows > 0){
		while($row = $result->fetch_assoc()){
			echo $row["TASKS"]."\n";
		}
	} else {
		die("No results.");
	}
} else {
	die("$conn->error");
}

$conn->close();
?>
