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

$email = $_REQUEST["email"];
$sql = 'SELECT * FROM user_records WHERE EMAIL="'.$email.'"';

if($result = $conn->query($sql)){
	if($result->num_rows > 0){
		while($row = $result->fetch_assoc()){
			$first_name = $row["FIRST_NAME"];
			$last_name = $row["LAST_NAME"];
			echo "$first_name $last_name";
		}
	} else {
		echo("No results");
	}	
} else {
	die("$conn->error");
}

$conn->close();
?>
