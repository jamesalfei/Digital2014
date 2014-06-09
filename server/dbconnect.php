<?php

	$con = mysqli_connect($db_host, $db_username, $db_password, $db_db);

	// Check connection
	if (mysqli_connect_errno())
	{
		echo "Failed to connect to MySQL: " . mysqli_connect_error();
	} else {

	}


?>