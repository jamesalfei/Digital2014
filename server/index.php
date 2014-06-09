<?php
ini_set('display_startup_errors',1);
ini_set('display_errors',1);
error_reporting(-1);


	$type = $_POST['content_type'];
	$request = $_POST['request_type'];

	if($type == "JSON"){
		require_once('functions.php');
		switch ($request) {
		    case "REGISTER":
				require_once 'login.php';
		       	echo addLogin($_POST['username'], $_POST['password']);
		        break;
			case "LOGIN":
				require_once 'login.php';
		       	echo login($_POST['username'], $_POST['password']);
		        break;
				
			case "SEARCH":
				JSON_Search($_POST['keyword'], $_POST['search']);
				break;

		}

	}else{
		
		?>
	
<!DOCTYPE html>
<html>

<head>
  <meta charset="UTF-8">
  <title>Go Away!</title>
</head>

<body>
<center></center><h1 style="position: absolute; left: 50%; top: 50%; margin-left: -285px; margin-top: -200px; text-align: center;">This is not the api you are looking for</h1><center></center>
  <img src="http://cdn.css-tricks.com/images/404.jpg" alt="Page Not Found (404)." style="position: absolute; left: 50%; top: 50%; margin-left: -285px; margin-top: -150px;">

</body>

</html>
	

<?}
?>