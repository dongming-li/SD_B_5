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
$location_id = $_REQUEST["location_id"];
$status = $_REQUEST["status"];

// Construct SQL query
$sql = 'SELECT * FROM order'.$company_id.' WHERE LOCATION_ID = '.$location_id.' AND STATUS = '.$status;

// Run query to return search results
if($result = $conn->query($sql)){
	if($result->num_rows > 0){
		while($row = $result->fetch_assoc()){
			echo "ORDER_ID:".$row["ORDER_ID"].
			    ",UPC:".$row["UPC"].
			    ",PRODUCT_NAME:".$row["PRODUCT_NAME"].
			    ",QUANTITY:".$row["QUANTITY"].
			    ",LOCATION_ID:".$row["LOCATION_ID"].
			    ",DESTINATION_ID:".$row["DESTINATION_ID"].
			    ",EXPECTED_DELIVERY_DATE:".$row["EXPECTED_DELIVERY_DATE"].
			    ",STATUS:".$row["STATUS"]."\n";
		}
	} else {
		die("No results.");
	}
} else {
	die("$conn->error");
}

$conn->close();
?>
