<?php

include 'DatabaseConfig.php';

$con = mysqli_connect($HostName, $HostUser, $HostPass, $DatabaseName);

$original_marker_id = $_POST['original_marker_id'];
$verification_user = $_POST['verification_user'];
$conflict = $_POST['conflict'];
$rank = $_POST['rank'];
$first_name = $_POST['first_name'];
$last_name = $_POST['last_name'];
$middle_name = $_POST['middle_name'];
$birth_date = $_POST['birth_date'];
$death_date = $_POST['death_date'];
$unit = $_POST['unit'];
$sub_unit = $_POST['sub_unit'];
$cemetery = $_POST['cemetery'];
$coordinates = $_POST['coordinates'];
$the_condition = $_POST['the_condition'];
$flag_present = $_POST['flag_present'];
$holder_present = $_POST['holder_present'];


// Create query statement
$Sql_Insert_Query = "INSERT INTO MarkerChange(original_marker_id, verification_user, conflict, rank, first_name, last_name, middle_name, birth_date, death_date, unit, sub_unit, cemetery, coordinates, the_condition, flag_present, holder_present) VALUES('$original_marker_id', '$verification_user', '$conflict', '$rank', '$first_name', '$last_name', '$middle_name', '$birth_date', '$death_date', '$unit', '$sub_unit', '$cemetery', '$coordinates', '$the_condition', '$flag_present', '$holder_present')";
$Sql_Update_Query = "UPDATE Marker SET needs_change='1' WHERE Marker_ID='$original_marker_id'";


// INSERT into Marker
if(mysqli_query($con, $Sql_Insert_Query) AND mysqli_query($con, $Sql_Update_Query)) {
	echo 'Change Submission Successful!';
}
else {
	echo mysqli_error($con);
}
mysqli_close($con);

?>