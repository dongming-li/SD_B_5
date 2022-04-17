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

$company_id = $_REQUEST["company_id"];
$upc = $_REQUEST["upc"];

if(!empty($_REQUEST["quantity"])){
	$quantity = $_REQUEST["quantity"];
	$sql = 'UPDATE product'.$company_id.' SET QUANTITY='.$quantity.' WHERE UPC='.$upc;
	if(!$conn->query($sql)){
		echo $conn->error;
	}
}

if(!empty($_REQUEST["bin_id"])){
	$bin_id = $_REQUEST["bin_id"];
	$sql = 'UPDATE product'.$company_id.' SET BIN_ID='.$bin_id.' WHERE UPC='.$upc;
	if(!$conn->query($sql)){
		echo $conn->error;
	}
}
if(!empty($_REQUEST["name"])){
	$product_name = $_REQUEST["name"];
	$sql = 'UPDATE product'.$company_id.' SET PRODUCT_NAME="'.$product_name.'" WHERE UPC='.$upc;
	if(!$conn->query($sql)){
		echo $conn->error;
	}
}

if(!empty($_REQUEST["description"])){
	$description = $_REQUEST["description"];
	$sql = 'UPDATE product'.$company_id.' SET DESCRIPTION="'.$description.'" WHERE UPC='.$upc;
	if(!$conn->query($sql)){
		echo $conn->error;
	}
}
if(!empty($_REQUEST["manufacturer_price"])){
	$manufacturer_price = $_REQUEST["manufacturer_price"];
	$sql = 'UPDATE product'.$company_id.' SET MANUFACTURER_PRICE="'.$manufacturer_price.'" WHERE UPC='.$upc;
	if(!$conn->query($sql)){
		echo $conn->error;
	}
}
if(!empty($_REQUEST["msrp"])){
	$msrp = $_REQUEST["msrp"];
	$sql = 'UPDATE product'.$company_id.' SET MSRP="'.$msrp.'" WHERE UPC='.$upc;
	if(!$conn->query($sql)){
		echo $conn->error;
	}
}
if(!empty($_REQUEST["location_id"])){
	$location_id = $_REQUEST["location_id"];
	$sql = 'UPDATE product'.$company_id.' SET LOCATION_ID='.$location_id.' WHERE UPC='.$upc;
	if(!$conn->query($sql)){
		echo $conn->error;
	}
}
if(!empty($_REQUEST["archive"])){
	$archive = $_REQUEST["archive"];
	$sql = 'UPDATE product'.$company_id.' SET ARCHIVE='.$archive.' WHERE UPC='.$upc;
	if(!$conn->query($sql)){
		echo $conn->error;
	}
}

$sql = 'SELECT * FROM product'.$company_id.' WHERE upc='.$upc;
if($result = $conn->query($sql)){
	if($result->num_rows > 0) {
		// Output data of each row
		while($row = $result->fetch_assoc()) {
			echo "UPC:".$row["UPC"].
			"-PRODUCT_NAME:".$row["PRODUCT_NAME"].
			"-QUANTITY:".$row["QUANTITY"].
			"-DESCRIPTION:".$row["DESCRIPTION"].
			"-BIN_ID:".$row["BIN_ID"].
			"-MANUFACTURER_PRICE:".$row["MANUFACTURER_PRICE"].
			"-MSRP:".$row["MSRP"].
			"-LOCATION_ID:".$row["LOCATION_ID"].
			"-ARCHIVE:".$row["ARCHIVE"];
		}
	} else {
		echo "0 results";
	}
} else{
	echo $conn->error;
}

$conn->close();
?>
