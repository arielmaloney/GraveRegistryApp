<?php

include 'DatabaseConfig.php';

$con = mysqli_connect($HostName, $HostUser, $HostPass, $DatabaseName);

$user = $_GET['user'];

// Create query statements
$Sql_Select_Past_Subs = "SELECT * FROM marker WHERE user='$user'";

// Get SELECT query result
$Past_Sub_Result = mysqli_query($con, $Sql_Select_Past_Subs);

// The array that will be sent
$return_array = array();

while ($row = mysqli_fetch_array($Past_Sub_Result, MYSQLI_ASSOC)) {
	
	$row_array['conflict'] = $row['conflict'];
	$row_array['rank'] = $row['rank'];
	$row_array['first_name'] = $row['first_name'];
	$row_array['last_name'] = $row['last_name'];
	$row_array['middle_name'] = $row['middle_name'];
	$row_array['birth_date'] = $row['birth_date'];
	$row_array['death_date'] = $row['death_date'];
	$row_array['unit'] = $row['unit'];
	$row_array['sub_unit'] = $row['sub_unit'];
	$row_array['cemetery'] = $row['cemetery'];
	$row_array['coordinates'] = $row['coordinates'];
	$row_array['the_condition'] = $row['the_condition'];
	$row_array['flag_present'] = $row['flag_present'];
	$row_array['holder_present'] = $row['holder_present'];
	$row_array['is_verified'] = $row['is_verified'];
	
	array_push($return_array,$row_array);
	
}

// echos Past submission information
if($Past_Sub_Result) {
	header('Content-type: application/json');
	echo '{"pastSubs":'.json_encode($return_array)."}";
}
else {
	echo mysqli_error($con);
}
mysqli_close($con);

?>