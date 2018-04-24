<?php

include 'DatabaseConfig.php';

$con = mysqli_connect($HostName, $HostUser, $HostPass, $DatabaseName);

$Marker_ID = $_GET['Marker_ID'];

// Create query statements
$Sql_Select_Entry = "SELECT * FROM marker WHERE Marker_ID = '$Marker_ID'";

// Get SELECT query result
$Entry_Result = mysqli_query($con, $Sql_Select_Entry);

// The array that will be sent
$return_array = array();

while ($row = mysqli_fetch_array($Entry_Result, MYSQLI_ASSOC)) {
	
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
	
	array_push($return_array,$row_array);
	
}

// echos Past submission information
if($Entry_Result) {
	header('Content-type: application/json');
	echo '{"graveEntry":'.json_encode($return_array)."}";
}
else {
	echo mysqli_error($con);
}
mysqli_close($con);

?>