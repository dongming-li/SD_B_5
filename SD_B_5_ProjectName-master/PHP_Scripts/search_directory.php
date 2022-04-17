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

if(!empty($_REQUEST["user_id"])){
	$user_id = $_REQUEST["user_id"];
	$sql = 'SELECT * FROM  user'.$company_id.' WHERE USER_ID='.$user_id;
} else if(!empty($_REQUEST["last_name"])){
	$last_name = $_REQUEST["last_name"];
	$sql = 'SELECT * FROM user'.$company_id.' WHERE LAST_NAME="'.$last_name.'"';
} else if(!empty($_REQUEST["first_name"])){
	$first_name = $_REQUEST["first_name"];
	$sql = 'SELECT * FROM user'.$company_id.' WHERE FIRST_NAME="'.$first_name.'"';
} else if(!empty($_REQUEST["location_id"])){
	$location_id = $_REQUEST["location_id"];
	$sql = 'SELECT * FROM user'.$company_id.' WHERE LOCATION_ID='.$location_id;
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
			echo "USER_ID:" . $row["USER_ID"].
			"-LOCATION_ID:" . $row["LOCATION_ID"].
			"-USER_TYPE:" . $row["USER_TYPE"].
			"-FIRST_NAME:" . $row["FIRST_NAME"].
			"-LAST_NAME:" . $row["LAST_NAME"].
			"-EMAIL:" . $row["EMAIL"].
			"-PHONE_NUMBER:" . $row["PHONE_NUMBER"].
			"-ADDRESS:" . $row["ADDRESS"].
			"-STATE:" . $row["STATE"].
			"-CITY:" . $row["CITY"].
			"-ZIPCODE:" . $row["ZIPCODE"].
			"-LATITUDE:" . $row["LATITUDE"].
			"-LONGITUDE:" . $row["LONGITUDE"].
			"-TASKS:".$row["TASKS"]."\n";
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
