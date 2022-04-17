
<?php

$servername = "mysql.cs.iastate.edu";
$username = "dbu309sdb5";
$password = "cazAqXCV";
$schema = "db309sdb5";
 
// Create Connection 
$conn = new mysqli($servername, $username, $password);

// Check connection
if ($conn->connect_error) {
        die("Connection failed: " . $conn->connect_error);
}
     
// require_once('dbConnect.php');




// $sql = "SELECT * FROM db309sdb5.maps" ;


 // $LATITUDE = $_POST['LATITUDE'];
 // $LONGITUDE = $_POST['LONGITUDE'];
 // $NAME = $_POST['NAME'];
 // $DESCRIPTION = $_POST['DESCRIPTION'];

 $LATITUDE = $_REQUEST['LATITUDE'];
 $LONGITUDE = $_REQUEST['LONGITUDE'];
 $NAME = $_REQUEST['NAME'];
 $DESCRIPTION = $_REQUEST['DESCRIPTION'];

 
 $Sql_Query = 'insert into db309sdb5.maps (LATITUDE, LONGITUDE, NAME, DESCRIPTION) VALUES ('.$LATITUDE.',"'.$LONGITUDE.'","'.$NAME.'","'.$DESCRIPTION.'")';


 
 if(mysqli_query($conn,$Sql_Query)){
 
 echo 'Data Inserted Successfully';
 
 }
 else{
 
 echo 'TryAgain';
 
 }

mysqli_close($con);




?>


