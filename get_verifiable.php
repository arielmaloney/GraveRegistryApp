<?php

include 'DatabaseConfig.php';

$con = mysqli_connect($HostName, $HostUser, $HostPass, $DatabaseName);

$user = $_GET['user'];

// Create query statements
$Sql_Select_Verifiable = "SELECT * FROM marker WHERE user != '$user' AND is_verified = '0' AND needs_change = '0'";

// Get SELECT query result
$Verifiable_Result = mysqli_query($con, $Sql_Select_Verifiable);

// The array that will be sent
$return_array = array();

while ($row = mysqli_fetch_array($Verifiable_Result, MYSQLI_ASSOC)) {
	
	$row_array['Marker_ID'] = $row['Marker_ID'];
	$row_array['conflict'] = $row['conflict'];
	$row_array['first_name'] = $row['first_name'];
	$row_array['last_name'] = $row['last_name'];
	$row_array['middle_name'] = $row['middle_name'];
	$row_array['cemetery'] = $row['cemetery'];
	$row_array['coordinates'] = $row['coordinates'];
	
	array_push($return_array,$row_array);
	
}

// echos Past submission information
if($Verifiable_Result) {
	header('Content-type: application/json');
	echo '{"verifiable":'.json_encode($return_array)."}";
}
else {
	echo mysqli_error($con);
}
mysqli_close($con);

?>