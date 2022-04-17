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
	die("Please provide the 'company_id'.");
}
$company_id = $_REQUEST["company_id"];
$sql = 'SELECT * FROM  store'.$company_id.' WHERE WAREHOUSE=1';

if($result = $conn->query($sql))
{	
	if($result->num_rows > 0) 
	{
		// Output data of each row
		while($row = $result->fetch_assoc()) 
		{
			echo $row["LOCATION_ID"].",";
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
