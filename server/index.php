<?php
ini_set('display_startup_errors',1);
ini_set('display_errors',1);
error_reporting(-1);


	$type = $_POST['content_type'];
	$request = $_POST['request_type'];

	if($type == "JSON"){
		require_once ('settings.php');
		require_once('dbconnect.php');
		switch ($request) {
		    case "REGISTER":
				require_once 'login.php';
		       	echo addLogin($_POST['username'], $_POST['password']);
		        break;
			case "LOGIN":
				require_once 'login.php';
		       	echo login($_POST['username'], $_POST['password']);
		        break;
				
			case "GETBEACONID":
				require_once 'beacon.php';
				echo getHoleDate($_POST['usrToken'], $_POST['beaconID']);
				break;
			case "ADDHOLESCORE":
				require_once 'GameScore.php';
				echo addHoleScore($_POST['usrToken'],$_POST['usrToken'], $_POST['usrToken']);
				break;
			case "ADDFINALSCORE":
				require_once 'GameScore.php';
				echo addFinalScore($_POST['courseID'], $_POST['score'], $_POST['usrToken'], $_POST['weather']);
				break;
			case "GETHOLESCORE":
				require_once 'GameScore.php';
				echo getYourHoleScores($_POST['usrToken'], $_POST['holeID']);
				break;
			case "GETFINALSCORE":
				require_once 'GameScore.php';
				echo getYourGameScores($_POST['usrToken'], $_POST['courseID']);
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