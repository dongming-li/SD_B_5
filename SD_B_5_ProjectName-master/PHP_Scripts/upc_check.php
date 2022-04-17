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
$location_id = $_REQUEST["location_id"];
$sql = 'SELECT UPC FROM  product'.$company_id.' WHERE UPC='.$upc.' AND LOCATION_ID='.$location_id;


if($result = $conn->query($sql))
{	
	if($result->num_rows > 0) 
	{
		// Output data of each row
		while($row = $result->fetch_assoc()) 
		{
			echo $row["UPC"];
		}
	} else 
	{
		echo "Item does not exist.";
	}
} else
{
	echo $conn->error;
}

$conn->close();
?>
