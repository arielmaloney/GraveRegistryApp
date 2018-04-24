<?php

include 'DatabaseConfig.php';

$con = mysqli_connect($HostName, $HostUser, $HostPass, $DatabaseName);

$Marker_ID = $_POST['Marker_ID'];
$is_verified = $_POST['is_verified'];

// Create query statement
$Sql_Update_Query = "UPDATE Marker SET is_verified = '$is_verified' WHERE Marker_ID = '$Marker_ID'";

// INSERT into Marker
if(mysqli_query($con, $Sql_Update_Query)) {
	echo 'Entry Verified as Accurate Successfully!';
}
else {
	echo mysqli_error($con);
}
mysqli_close($con);

?>