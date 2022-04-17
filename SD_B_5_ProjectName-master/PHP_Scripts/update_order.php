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
if(empty($_REQUEST["company_id"])){
	die("Please provide the 'company_id'.");
} else if(empty($_REQUEST["order_id"])){
	die("Please provide the 'order_id'.");
}
$company_id = $_REQUEST["company_id"];
$order_id = $_REQUEST["order_id"];

// UPDATE user SET VARIABLE=VALUE WHERE ORDER_ID=ORDER_ID

if(!empty($_REQUEST["status"])){
	$status = $_REQUEST["status"];
	$sql = 'UPDATE order'.$company_id.' SET STATUS='.$status.' WHERE ORDER_ID='.$order_id;
	if(!$con->query($sql)){
		die("$con->error");
	}
}

// Return Search Query On USER_ID
$sql = 'SELECT * FROM order'.$company_id.' WHERE ORDER_ID='.$order_id;
if($result = $con->query($sql)){
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
	} else{
		die("There is no order with the order_id: $order_id");
	}
} else{
	die("$con->error");
}
?>
