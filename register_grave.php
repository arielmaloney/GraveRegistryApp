<?php

include 'DatabaseConfig.php';

$con = mysqli_connect($HostName, $HostUser, $HostPass, $DatabaseName);

$user = $_POST['user'];
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
$is_verified = 0;
$needs_change = 0;


// Create query statement
$Sql_Insert_Query = "INSERT INTO Marker(user, conflict, rank, first_name, last_name, middle_name, birth_date, death_date, unit, sub_unit, cemetery, coordinates, the_condition, flag_present, holder_present, is_verified, needs_change) VALUES('$user', '$conflict', '$rank', '$first_name', '$last_name', '$middle_name', '$birth_date', '$death_date', '$unit', '$sub_unit', '$cemetery', '$coordinates', '$the_condition', '$flag_present', '$holder_present', '$is_verified', '$needs_change')";

// INSERT into Marker
if(mysqli_query($con, $Sql_Insert_Query)) {
	echo 'Grave Registration Successful!';
}
else {
	echo mysqli_error($con);
}
mysqli_close($con);

?>