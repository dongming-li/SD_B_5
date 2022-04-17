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

if(empty($_REQUEST["company_id"])){
	die("Please provide the 'company_id'");
}
$company_id = $_REQUEST["company_id"];

$list = $_REQUEST["item_list"];

$count = (int) $_REQUEST["count"];

if(empty($_REQUEST["location_id"])){
	die("Please provide the 'location_id'");
}
$location_id = $_REQUEST["location_id"];

if(empty($_REQUEST["destination_id"])){
	die("Please provide the 'destination_id'");
}
$destination_id = $_REQUEST["destination_id"];

if(empty($_REQUEST["date"])){
	die("Please provide the 'expected_delivery_date'");
}
$date = $_REQUEST["date"];

if(empty($_REQUEST["order_id"])){
	$sql = 'SELECT MAX(ORDER_ID) AS ORDER_ID FROM order'.$company_id;
	if($result = $conn->query($sql)){
		if($result->num_rows > 0){
			$row = $result->fetch_assoc();
			$order_id = ((int) $row["ORDER_ID"]) + 1;
		} else {
			$order_id = 1;
		}
	} else {
		die("$conn->error");
	}
} else {
	$order_id = $_REQUEST["order_id"];
}

$split = explode(",",$list);

for($i = 0; $i<$count; $i++){
//	if(substr_count($split[$i], "x") != 1){
//		die("Cannot split item string, missing 'x', or there is no 'x'.");
//	}
	$split_again = explode("x",$split[$i]);
	$upc = (int) $split_again[0];
	$quantity = (int) $split_again[1];
	$product_name = "Jason Juice";
	//if($result = $conn->query('SELECT PRODUCT_NAME FROM product'.$company_id.' WHERE UPC='.$upc)){
	//	if($result->num_rows > 0){
	//		$row = $result->fetch_assoc();
	//		$product_name = $row["PRODUCT_NAME"];
	//	} else {
	//		die("Product doesn't exist");
	//	}
//	} else {
//		die("$conn->error");
//	}
	$sql = 'INSERT INTO order'.$company_id.' (ORDER_ID, UPC, PRODUCT_NAME, QUANTITY, LOCATION_ID, DESTINATION_ID, EXPECTED_DELIVERY_DATE, STATUS) VALUES('.$order_id.','.$upc.',"'.$product_name.'",'.$quantity.','.$location_id.','.$destination_id.',"'.$date.'",1)';
	if(!$conn->query($sql)){
		die("$conn->error");
	}
}

$conn->close();
?>
