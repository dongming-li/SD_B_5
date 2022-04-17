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

$sql = 'SELECT MAX(LOCATION_ID) AS LOCATION_ID FROM store'.$company_id;
if($result = $conn->query($sql)){
	if($result->num_rows > 0){
		$row = $result->fetch_assoc();
		$num_stores = (int) $row["LOCATION_ID"];
	}else{
		die("There are zero results.");
	}
} else {
	die("$conn->error");
}

for($i=1; $i<($num_stores+1); $i++){

	if(!empty($_REQUEST["upc"])){
		$upc = $_REQUEST["upc"];
		$sql = 'SELECT * FROM  product'.$company_id.'_'.$i.' WHERE UPC='.$upc;

	} else if(!empty($_REQUEST["name"])){
		$name = $_REQUEST["name"];
		$sql = 'SELECT * FROM product'.$company_id.'_'.$i.' WHERE PRODUCT_NAME="'.$name.'"';	

	} else if(!empty($_REQUEST["location_id"])){
		$location_id = $_REQUEST["location_id"];
		$sql = 'SELECT * FROM product'.$company_id.'_'.$i.' WHERE LOCATION_ID='.$location_id;	
	} else {
		echo "Unable to obtain input. Please pass a upc or name.";
	}

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
		}
	} else
	{
		die("$conn->error");
	}
}
$conn->close();
?>
