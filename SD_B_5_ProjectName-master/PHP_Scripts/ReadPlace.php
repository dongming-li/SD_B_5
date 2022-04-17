
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




$sql = "SELECT * FROM db309sdb5.maps" ;

// $r = mysqli_query($con,$sql);

$result = array();

// while($row = mysqli_fetch_array($r)){
//     array_push($result,array(
//         'LATITUDE'=>$row['LATITUDE'],
//         'LONGITUDE'=>$row['LONGITUDE'],
//         'NAME'=>$row['NAME'],
//         'DESCRIPTION'=>$row['DESCRIPTION']
//     ));
// }

if($resultt = $conn->query($sql))
{       
        if($resultt->num_rows > 0) 
        {
                // Output data of each row
                while($row = $resultt->fetch_assoc()) 
                {

                        // array_push($result ,array( 

    // ));

					array_push($result,array(
				        // 'LATITUDE'=>$row['LATITUDE'],
				        // 'LONGITUDE'=>$row['LONGITUDE'],
				        'NAME'=>$row['NAME'],
				        'DESCRIPTION'=>$row['DESCRIPTION'],

                'ADDRESS'=>$row['ADDRESS'],

    				));

                			

                		// echo
                  //       	"LATITUDE:" . $row["LATITUDE"].
                  //       "	LONGITUDE:" . $row["LONGITUDE"].
                  //       "	NAME:" . $row["NAME"].
                  //       "	DESCRIPTION:" . $row["DESCRIPTION"].
                  //       "<br>";




                        // echo $result;
                }
        } else 
        {
                echo "0 results";
        }
} else
{
        echo $conn->error;
}



echo json_encode(array('result'=>$result));

mysqli_close($con);




?>


