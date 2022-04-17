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

if(!empty($_REQUEST["company_id"])){
	$company_id = $_REQUEST["company_id"];
} else {
	die("Please provide 'company_id' to update company's tables.");
}

if(!empty($_REQUEST["upc"])){
	$upc = $_REQUEST["upc"];
} else {
	die("Please provide 'upc' to find or add.");
}

if(!empty($_REQUEST["quantity"])){
	$quantity = $_REQUEST["quantity"];
} else {
	die("Please provide 'quantity' to be updated.");
}

if(!empty($upc)){
	
	$sql = 'Select * FROM product'.$company_id.' WHERE UPC='.$upc;
	if($result = $conn->query($sql)){
		
		if($result->num_rows > 0){
			$row = $result->fetch_assoc();
			$exists = 1;
			$current_quantity = (int) $row["QUANTITY"];
			$current_quantity += (int) $quantity;
		} else{
			$exists = 0;
			$current_quantity = 0;
			$current_quantity += (int) $quantity;
			$name = $_REQUEST["name"];
			$description = $_REQUEST["description"];
			$bin_id = $_REQUEST["bin_id"];
			$manufacturer_price = $_REQUEST["manufacturer_price"];
			$msrp = $_REQUEST["msrp"];
			$location_id = $_REQUEST["location_id"];
		}
	} else{
		$exists = 0;
		$current_quantity = 0;
		$current_quantity += (int) $quantity;
		$name = $_REQUEST["name"];
		$description = $_REQUEST["description"];
		$bin_id = $_REQUEST["bin_id"];
		$manufacturer_price = $_REQUEST["manufacturer_price"];
		$msrp = $_REQUEST["msrp"];
		$location_id = $_REQUEST["location_id"];
	}
}

if($exists == 0) {
	
	if(!empty($upc) || !empty($name) || !empty($quantity) || !empty($bin_id) || !empty($location_id)) {			
		$sql = 'INSERT INTO product'.$company_id.' (UPC,PRODUCT_NAME,QUANTITY,DESCRIPTION,BIN_ID,MANUFACTURER_PRICE,MSRP,LOCATION_ID, ARCHIVE) VALUES ('.$upc.',"'.$name.'","'.$current_quantity.'","'.$description.'","'.$bin_id.'","'.$manufacturer_price.'","'.$msrp.'","'.$location_id.'",0)';

	} else {
		die("Unable to obtain input. Please pass appropriate parameters.");
	}

} else if(!empty($upc) || $current_quantity != 0 && $exists == 1){
	 $sql = 'UPDATE product'.$company_id.' SET QUANTITY="'.$current_quantity.'" WHERE UPC='.$upc;

} else{
	die("Unable to obtain input. Please pass appropriate parameters.");
}

if($conn->query($sql))
{
	$sql = 'SELECT * FROM product'.$company_id.' WHERE UPC='.$upc;
	if($result = $conn->query($sql))
	{	
		if($result->num_rows > 0) 
		{
			// Output data of each row
			while($row = $result->fetch_assoc()) 
			{
				echo "UPC:" . $row["UPC"].
				"-PRODUCT_NAME:" . $row["PRODUCT_NAME"].
				"-QUANTITY:" . $row["QUANTITY"].
				"-DESCRIPTION:" . $row["DESCRIPTION"].
				"-BIN_ID:" . $row["BIN_ID"].
				"-MANUFACTURER_PRICE:" . $row["MANUFACTURER_PRICE"].
				"-MSRP:" . $row["MSRP"].
				"-LOCATION_ID:" . $row["LOCATION_ID"]. 
				"-ARCHIVE:" . $row["ARCHIVE"] . "\n";
			}
		} else 
		{
			die("0 results");
		}
	} else
	{
		die("$conn->error");
	}
} else
{
	die("$conn->error");
}

$conn->close();
?>
