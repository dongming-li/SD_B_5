<?php
// Set Connection Variables
$servername = "mysql.cs.iastate.edu";
$username = "dbu309sdb5";
$password = "cazAqXCV";
$schema = "db309sdb5";

// Create Connection
$con = new mysqli($servername, $username, $password, $schema);

// Check Connection
if($con->connect_error){
	die("Connection failed: ".$con->connect_error);
}

// Check And Store Variables From Post
if(empty($_REQUEST["company_id"])){
	die("Please provide the 'company_id'.");
} else if(empty($_REQUEST["location_id"])){
	die("Please provide the 'location__id'.");
}
$company_id = $_REQUEST["company_id"];
$location_id = $_REQUEST["location_id"];

// UPDATE user SET VARIABLE=VALUE WHERE USER_ID=USER_ID

if(!empty($_REQUEST["email"])){
	$email = $_REQUEST["email"];
	$sql = 'UPDATE store'.$company_id.' SET EMAIL="'.$email.'" WHERE LOCATION_ID='.$location_id;
	if(!$con->query($sql)){
		die("$con->error");
	}
}
if(!empty($_REQUEST["phone"])){
	$phone_number = $_REQUEST["phone"];
	$sql = 'UPDATE store'.$company_id.' SET PHONE_NUMBER='.$phone_number.' WHERE LOCATION_ID='.$location_id;
	if(!$con->query($sql)){
		die("$con->error");
	}
}
if(!empty($_REQUEST["address"])){
	$address = $_REQUEST["address"];
	$sql = 'UPDATE store'.$company_id.' SET ADDRESS="'.$address.'" WHERE LOCATION_ID='.$location_id;
	if(!$con->query($sql)){
		die("$con->error");
	}
}
if(!empty($_REQUEST["city"])){
	$city = $_REQUEST["city"];
	$sql = 'UPDATE store'.$company_id.' SET CITY="'.$city.'" WHERE LOCATION_ID='.$location_id;
	if(!$con->query($sql)){
		die("$con->error");
	}
}
if(!empty($_REQUEST["state"])){
	$state = $_REQUEST["state"];
	$sql = 'UPDATE store'.$company_id.' SET STATE="'.$state.'" WHERE LOCATION_ID='.$location_id;
	if(!$con->query($sql)){
		die("$con->error");
	}
}
if(!empty($_REQUEST["zip_code"])){
	$zipcode = $_REQUEST["zip_code"];
	$sql = 'UPDATE store'.$company_id.' SET ZIPCODE='.$zipcode.' WHERE LOCATION_ID='.$location_id;
	if(!$con->query($sql)){
		die("$con->error");
	}
}
if(!empty($_REQUEST["lat"])){
	$lat = $_REQUEST["lat"];
	$sql = 'UPDATE store'.$company_id.' SET LATITUDE='.$lat.' WHERE LOCATION_ID='.$location_id;
	if(!$con->query($sql)){
		die("$con->error");
	}
}
if(!empty($_REQUEST["long"])){
	$long = $_REQUEST["long"];
	$sql = 'UPDATE store'.$company_id.' SET LONGITUDE='.$long.' WHERE LOCATION_ID='.$location_id;
	if(!$con->query($sql)){
		die("$con->error");
	}
}
if(!empty($_REQUEST["manager_id"])){
	$manager_id = $_REQUEST["manager_id"];
	$sql = 'UPDATE store'.$company_id.' SET MANAGER_ID='.$manager_id.' WHERE LOCATION_ID='.$lcoation_id;
	if(!$con->query($sql)){
		die("$con->error");
	}
}
if(!empty($_REQUEST["description"])){
	$description = $_REQUEST["description"];
	$sql = 'UPDATE store'.$company_id.' SET DESCRIPTION="'.$description.'" WHERE LOCATION_ID='.$location_id;
	if(!$con->query($sql)){
		die("$con->error");
	}
}

// Return Search Query On USER_ID
$sql = 'SELECT * FROM store'.$company_id.' WHERE LOCATION_ID='.$location_id;
if($result = $con->query($sql)){
	if($result->num_rows > 0){
		while($row = $result->fetch_assoc()){
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
	} else{
		die("There is no location with the location_id: $location_id");
	}
} else{
	die("$con->error");
}
?>
