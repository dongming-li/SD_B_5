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
$company_id = $_REQUEST["company_id"];
$order_id = $_REQUEST["order_id"];

// Construct SQL query
$sql = 'SELECT * FROM order'.$company_id.' WHERE ORDER_ID = '.$order_id;

// Run query to return search results
if($result = $conn->query($sql)){
	if($result->num_rows > 0){
		while($row = $result->fetch_assoc()){
			echo "ORDER_ID:".$row["ORDER_ID"]."\n";
		}
	} else {
		die("No results.");
	}
} else {
	die("$conn->error");
}

$conn->close();
?>
