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

}


function getYourGameScores($tokenw,$CourseID){

}










?>