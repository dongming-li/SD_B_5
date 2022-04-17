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

// Check for variables
if(empty($_REQUEST["company_id"])){
	die("Please provide 'company_id'.");
}
if(empty($_REQUEST["upc"])){
	die("Please provide 'upc' to locate the product.");
}
if(empty($_REQUEST["delete"])){
	die("Please provide 'delete' to determine which action to perform.");
}
$company_id = $_REQUEST["company_id"];
$upc = $_REQUEST["upc"];
$delete = (int) $_REQUEST["delete"];

if($delete==1){
	$sql = 'DELETE FROM product'.$company_id.' WHERE UPC='.$upc;
} else if($delete==2){
	
	if(empty($_REQUEST["quantity"])){
		die("Please provie 'quantity' to be reduced.");
	}
	$quantity = (int) $_REQUEST["quantity"];
	
	if($result = $conn->query('SELECT * FROM product'.$company_id.' WHERE UPC='.$upc)){
		if($result->num_rows>0){
			$row = $result->fetch_assoc();
			$current_quantity = (int) $row["QUANTITY"];
			if($current_quantity > $quantity){
				$quantity = (int) $current_quantity - (int) $quantity;	
			} else {
				//Set to zero or delete
				$quantity = 0;
			}
		} else {
			die("0 results");
		}
	} else {
		die ("$conn->error");
	}
	$sql = 'UPDATE product'.$company_id.' SET QUANTITY='.$quantity.' WHERE UPC='.$upc;

} else {
	die("The parameter 'delete' may only be 1 (Delete item) or 2 (Reduce quantity).");
}

if($conn->query($sql)) {
	$sql = 'SELECT * FROM product'.$company_id.' WHERE UPC='.$upc;
	if($result = $conn->query($sql)) {	
		if($result->num_rows > 0) {
			// Output data of each row
			while($row = $result->fetch_assoc()) {
				echo "UPC:" . $row["UPC"].
				"-PRODUCT_NAME:" . $row["PRODUCT_NAME"].
				"-QUANTITY:" . $row["QUANTITY"].
				"-DESCRIPTION:" . $row["DESCRIPTION"].
				"-BIN_ID:" . $row["BIN_ID"].
				"-MANUFACTURER_PRICE:" . $row["MANUFACTURER_PRICE"].
				"-MSRP:" . $row["MSRP"].
				"-LOCATION_ID:" . $row["LOCATION_ID"]. 
				"-ARCHIVE:".$row["ARCHIVE"]."\n";
			}
		} else {
			die("0 results");
		}
	} else {
		die("$conn->error");
	}
} else {
	die("$conn->error");
}
$conn->close();
?>
