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
$location_id = $_REQUEST["location_id"];
$sql = 'SELECT * FROM  product'.$company_id.' WHERE LOCATION_ID='.$location_id;

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
		echo "0 results";
	}
} else
{
	echo $conn->error;
}

$conn->close();
?>
