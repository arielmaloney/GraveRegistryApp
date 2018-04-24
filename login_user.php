<?php

if($_SERVER['REQUEST_METHOD']=='POST') {
	
	include 'DatabaseConfig.php';
	
	$con = mysqli_connect($HostName, $HostUser, $HostPass, $DatabaseName);
	
	$email = $_POST['email'];
	$password = $_POST['password'];
	
	$Sql_Select_Query = "SELECT * from user WHERE email='$email' AND password='$password'";
	
	$check = mysqli_fetch_array(mysqli_query($con, $Sql_Select_Query));
	
	if(isset($check)) {
		echo "Successful Login!";
	}
	else {
		echo "Invalid Email or Password. Try again.";
	}
}
else {
	echo "Check Again";
}

mysqli_close($con);

?>