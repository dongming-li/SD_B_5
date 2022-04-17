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

if(!empty($_REQUEST["location_id"])){
	$location_id = $_REQUEST["location_id"];
	$sql = 'SELECT * FROM  store'.$company_id.' WHERE LOCATION_ID='.$location_id;
} else {
	echo "Unable to obtain input.";
}

if($result = $conn->query($sql))
{	
	if($result->num_rows > 0) 
	{
		// Output data of each row
		while($row = $result->fetch_assoc()) 
		{
			echo "LOCATION_ID:" . $row["LOCATION_ID"].
			"-LATITTUDE:" . $row["LATITUDE"].
			"-LONGITUDE:" . $row["LONGITUDE"].
			"-MANAGER_ID:" . $row["MANAGER_ID"].
			"-PHONE_NUMBER:" . $row["PHONE_NUMBER"].
			"-COMPANY_ID:" . $row["COMPANY_ID"].
			"-EMAIL:" . $row["EMAIL"].
			"-ADDRESS:" . $row["ADDRESS"].
			"-STATE:" . $row["STATE"].
			"-CITY:" . $row["CITY"].
			"-ZIPCODE:" . $row["ZIPCODE"].
			"-DESCRIPTION:" . $row["DESCRIPTION"].
			"-WAREHOUSE:" . $row["WAREHOUSE"]."\n";
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
