<?php

function addHoleScore($holeID,$score,$tokenw){
	global $con;
	session_start();	
	if($_SESSION['token'] == $tokenw){
	$user = $_SESSION['user'] ; 
	$sql = "INSERT INTO Scores(HoleID,Score,UserID,Date) VALUES('$holeID','$score','$user',NOW());";
		if (mysqli_query($con, $sql) == TRUE) {
			$response = TRUE;
		}else{
			die('Error: ' . mysqli_error($con));
	
			$response = FALSE;
		}
	}else{
		$response = FALSE;
	}
	$reply = array();
	$reply['response'] = $response;
	return json_encode($reply);	
}

function addFinalScore($courseID,$score,$tokenw,$weather){
	
	global $con;
	session_start();	
	if($_SESSION['token'] == $tokenw){
	$user = $_SESSION['user'] ; 
	$sql = "INSERT INTO Game(CourseID,Score,Weather,UserID,Date) VALUES('$courseID','$score','$weather','$user',NOW());";
		if (mysqli_query($con, $sql) == TRUE) {
			$response = TRUE;
		}else{
			die('Error: ' . mysqli_error($con));
	
			$response = FALSE;
		}
	}else{
		$response = FALSE;
	}
	$reply = array();
	$reply['response'] = $response;
	return json_encode($reply);	
	
	
}

function getYourHoleScores($tokenw,$holeID){
	$reply = array();
		session_start();	
		if($_SESSION['token'] == $tokenw){
			$reply['response']  = TRUE;
			$user = $_SESSION['user'];
			global $con;
			$sql  = "SELECT * FROM Scores WHERE HoleID = '$holeID' && UserID = '$user'";
			$resp;
			if (!mysqli_query($con,$sql)){
				$resp = FALSE;
				die('Error: ' . mysqli_error($con));
			}else{
				$resp = mysqli_query($con,$sql);
			}
			while($row = mysqli_fetch_array($resp)) {
			  //Add this row to the reply
		    $response['HoleID'] = $row['HoleID'];
		    $response['Socre'] = $row['Score'];
			$response['Date'] = $row['Date'];
		    $reply['Data'][] = $response;
			}		
		} else{
			$reply['response']  = 'ERROR';
			$reply['message']  = 'invalid token have you tried to login';
		}
		return json_encode($reply);

}


function getYourGameScores($tokenw,$CourseID){
	$reply = array();
	session_start();	
	if($_SESSION['token'] == $tokenw){
		$reply['response']  = TRUE;
		$user = $_SESSION['user'];
		global $con;
		$sql  = "SELECT * FROM Game WHERE CourseID = '$CourseID'&& UserID = '$user'";
		$resp;
		if (!mysqli_query($con,$sql)){
			$resp = FALSE;
			die('Error: ' . mysqli_error($con));
		}else{
			$resp = mysqli_query($con,$sql);
		}
		while($row = mysqli_fetch_array($resp)) {
		  //Add this row to the reply
	    $response['CourseID'] = $row['CourseID'];
	    $response['Socre'] = $row['Score'];
		$response['Weather'] = $row['Weather'];
	    $reply['Data'][] = $response;
		}		
	} else{
		$reply['response']  = 'ERROR';
		$reply['message']  = 'invalid token have you tried to login';
	}
	return json_encode($reply);
}










?>