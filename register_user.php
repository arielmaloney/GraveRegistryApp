<?php

include 'DatabaseConfig.php';

$con = mysqli_connect($HostName, $HostUser, $HostPass, $DatabaseName);

$first_name = $_POST['first_name'];
$last_name = $_POST['last_name'];
$email = $_POST['email'];
$password = $_POST['password'];

// Create query statements
$Sql_Select_Query = "SELECT * FROM user WHERE email='$email'";
$Sql_Insert_Query = "INSERT INTO user (first_name,last_name,email,password) VALUES ('$first_name','$last_name','$email','$password')";

// Get SELECT query result
$Select_Result = mysqli_query($con, $Sql_Select_Query);

// if SELECT query returns a result than email is already registered else INSERT
if(mysqli_num_rows($Select_Result)) {
	echo 'Email already exists.';
}
else if(mysqli_query($con, $Sql_Insert_Query)) {
	echo 'Successful Registration!';
}
else {
	echo mysqli_error($con);
}
mysqli_close($con);

?>