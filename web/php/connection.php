<?php
	$servername = "localhost";

	//Para derek con estas credenciales 
	$username = "user";
	$password = "access";

	//Para doña Nicolle:
	/*$username = "root";
	$password = "";*/
	
	$dbname = "alnitakdb";
	$conn = new mysqli($servername, $username, $password, $dbname);
	// Check connection
	if ($conn->connect_error) {
	  die("Connection failed: " . $conn->connect_error);
	}
?>