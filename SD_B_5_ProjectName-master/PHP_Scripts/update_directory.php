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
} else if(empty($_REQUEST["user_id"])){
	die("Please provide the 'user_id'.");
}
$company_id = $_REQUEST["company_id"];
$user_id = $_REQUEST["user_id"];

$user_record_email;
$sql = 'SELECT * FROM user'.$company_id.' WHERE USER_ID='.$user_id;
if($result = $con->query($sql)){
	if($result->num_rows > 0){
		while($row = $result->fetch_assoc()){
			$user_record_email = $row["EMAIL"];
		}
	} else {
		die("Cannot locateuser email to update in user_records.");
	}
}


// UPDATE user SET VARIABLE=VALUE WHERE USER_ID=USER_ID

if(!empty($_REQUEST["location_id"])){
	$location_id = $_REQUEST["location_id"];
	$sql = 'UPDATE user'.$company_id.' SET LOCATION_ID='.$location_id.' WHERE USER_ID='.$user_id;
	if(!$con->query($sql)){
		die("$con->error");
	}
	$sql = 'UPDATE user_records SET LOCATION_ID='.$location_id.' WHERE EMAIL="'.$user_record_email.'"';
	if(!$con->query($sql)){
		die("$con->error");
	}
}
if(!empty($_REQUEST["user_type"])){
	$user_type = $_REQUEST["user_type"];
	$sql = 'UPDATE user'.$company_id.' SET USER_TYPE='.$user_type.' WHERE USER_ID='.$user_id;
	if(!$con->query($sql)){
		die("$con->error");
	}
	$sql = 'UPDATE user_records SET USER_TYPE='.$user_type.' WHERE EMAIL="'.$user_record_email.'"';
	if(!$con->query($sql)){
		die("$con->error");
	}
}
if(!empty($_REQUEST["first_name"])){
	$first_name = $_REQUEST["first_name"];
	$sql = 'UPDATE user'.$company_id.' SET FIRST_NAME="'.$first_name.'" WHERE USER_ID='.$user_id;
	if(!$con->query($sql)){
		die("$con->error");
	}
	$sql = 'UPDATE user_records SET FIRST_NAME="'.$first_name.'" WHERE EMAIL="'.$user_record_email.'"';
	if(!$con->query($sql)){
		die("$con->error");
	}
}
if(!empty($_REQUEST["last_name"])){
	$last_name = $_REQUEST["last_name"];
	$sql = 'UPDATE user'.$company_id.' SET LAST_NAME="'.$last_name.'" WHERE USER_ID='.$user_id;
	if(!$con->query($sql)){
		die("$con->error");
	}
	$sql = 'UPDATE user_records SET LAST_NAME="'.$last_name.'" WHERE EMAIL="'.$user_record_email.'"';
	if(!$con->query($sql)){
		die("$con->error");
	}
}
if(!empty($_REQUEST["email"])){
	$email = $_REQUEST["email"];
	$sql = 'UPDATE user'.$company_id.' SET EMAIL="'.$email.'" WHERE USER_ID='.$user_id;
	if(!$con->query($sql)){
		die("$con->error");
	}
}
if(!empty($_REQUEST["phone_number"])){
	$phone_number = $_REQUEST["phone_number"];
	$sql = 'UPDATE user'.$company_id.' SET PHONE_NUMBER='.$phone_number.' WHERE USER_ID='.$user_id;
	if(!$con->query($sql)){
		die("$con->error");
	}
}
if(!empty($_REQUEST["address"])){
	$address = $_REQUEST["address"];
	$sql = 'UPDATE user'.$company_id.' SET ADDRESS="'.$address.'" WHERE USER_ID='.$user_id;
	if(!$con->query($sql)){
		die("$con->error");
	}
}
if(!empty($_REQUEST["city"])){
	$city = $_REQUEST["city"];
	$sql = 'UPDATE user'.$company_id.' SET CITY="'.$city.'" WHERE USER_ID='.$user_id;
	if(!$con->query($sql)){
		die("$con->error");
	}
}
if(!empty($_REQUEST["state"])){
	$state = $_REQUEST["state"];
	$sql = 'UPDATE user'.$company_id.' SET STATE="'.$state.'" WHERE USER_ID='.$user_id;
	if(!$con->query($sql)){
		die("$con->error");
	}
}
if(!empty($_REQUEST["zip_code"])){
	$zipcode = $_REQUEST["zip_code"];
	$sql = 'UPDATE user'.$company_id.' SET ZIPCODE='.$zipcode.' WHERE USER_ID='.$user_id;
	if(!$con->query($sql)){
		die("$con->error");
	}
}
if(!empty($_REQUEST["lat"])){
	$lat = $_REQUEST["lat"];
	$sql = 'UPDATE user'.$company_id.' SET LATITUDE='.$lat.' WHERE USER_ID='.$user_id;
	if(!$con->query($sql)){
		die("$con->error");
	}
}
if(!empty($_REQUEST["long"])){
	$long = $_REQUEST["long"];
	$sql = 'UPDATE user'.$company_id.' SET LONGITUDE='.$long.' WHERE USER_ID='.$user_id;
	if(!$con->query($sql)){
		die("$con->error");
	}
}
if(!empty($_REQUEST["tasks"])){
	$tasks = $_REQUEST["tasks"];
	$sql = 'UPDATE user'.$company_id.' SET TASKS="'.$tasks.'" WHERE USER_ID='.$user_id;
	if(!$con->query($sql)){
		die("$con->error");
	}
}

// Return Search Query On USER_ID
$sql = 'SELECT * FROM user'.$company_id.' WHERE USER_ID='.$user_id;
if($result = $con->query($sql)){
	if($result->num_rows > 0){
		while($row = $result->fetch_assoc()){
			echo "USER_ID:".$row["USER_ID"].
			"-LOCATION_ID:".$row["LOCATION_ID"].
			"-USER_TYPE:".$row["USER_TYPE"].
			"-FIRST_NAME".$row["FIRST_NAME"].
			"-LAST_NAME:".$row["LAST_NAME"].
			"-EMAIL:".$row["EMAIL"].
			"-PHONE_NUMBER:".$row["PHONE_NUMBER"].
			"-ADDRESS:".$row["ADDRESS"].
			"-STATE:".$row["STATE"].
			"-CITY:".$row["CITY"].
			"-ZIPCODE:".$row["ZIPCODE"].
			"-LATITUDE:".$row["LATITUDE"].
			"-LONGITUDE:".$row["LONGITUDE"].
			"-TASKS:".$row["TASKS"]."\n";
		}
	} else{
		die("There is no user with the user_id: $user_id");
	}
} else{
	die("$con->error");
}
?>
