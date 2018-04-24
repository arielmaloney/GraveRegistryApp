<?php

include 'DatabaseConfig.php';

$con = mysqli_connect($HostName, $HostUser, $HostPass, $DatabaseName);

$email = $_GET['email'];

// Create query statements
$Sql_Select_User_Query = "SELECT * FROM user WHERE email='$email'";

// Get SELECT query result
$User_Result = mysqli_query($con, $Sql_Select_User_Query);
$User_array = mysqli_fetch_array($User_Result, MYSQLI_ASSOC);

$user_id = $User_array["User_ID"];
$user_firstname = $User_array["first_name"];
$user_lastname = $User_array["last_name"];


// echos user data
if($User_Result) {
	$data = array('userID' => $user_id, 'userEmail' => $email, 'userFirstName' => $user_firstname, 'userLastName' => $user_lastname);
	header('Content-type: application/json');
	echo (json_encode($data));
}
else {
	echo mysqli_error($con);
}
mysqli_close($con);

?>