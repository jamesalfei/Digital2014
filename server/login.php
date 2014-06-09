<?php
ini_set('display_startup_errors',1);
ini_set('display_errors',1);
error_reporting(-1);

//login methood
function login($user, $paswrd){
	global $con;
	$sql  = "SELECT * FROM Account WHERE Username = '$user'";
	$response;
	$reply = array();
	if (!mysqli_query($con,$sql)){
		die('Error: ' . mysqli_error($con));
		$response = FALSE;
	}else{
		$result = mysqli_fetch_array(mysqli_query($con,$sql));
		$demo = hash('sha512', $paswrd);
		//echo $demo+'<br/>';
		$password =  hash("sha512",$demo.$result['Salt']); 
		//echo $password;

		if($result['Password'] == $password){
			$response = TRUE;
			require_once 'functions.php';
			session_start();
			$token  = genToken();
			$_SESSION['token'] = $token;
			$reply['token'] = $token;
		} else{
			$response = FALSE;
		}
	}
	$reply['response'] = $response;
	return json_encode($reply);
}


function addLogin($user, $pass){
	global $con;
	$response;
	$paasword = hash("sha512", $pass);
	$salt = hash("sha512", time());
	$nwpass = hash("sha512",($paasword.$salt));
	$sql = "INSERT INTO Account(Username , Password, Salt) VALUES('$user', '$nwpass','$salt');";


	if (mysqli_query($con, $sql) == TRUE) {
		 $myemail = "yoyoambition@gmail.com" ;
	 	 $subject = "Sign Up yoyoambiton" ;
	  	$message = "Good Day to you. It seems that you have signed up for Yoyoambition.com .";
		//require 'email.php';
		//sendEmail($user,$myemail,$subject,$message);

		$response = TRUE;
	}else{
		die('Error: ' . mysqli_error($con));

		$response = FALSE;
	}
	$reply = array();
	$reply['response'] = $response;
	return json_encode($reply);

}
//update person
function updateLogin($user,$pass){
	global $con;
	$response;
	$paasword = hash("sha512", $pass);
	$newsalt = hash("sha512", time());
	$newhash = hash("sha512",($paasword.$newsalt));

	$sql = "UPDATE Account SET Salt='$newsalt', Password='$newhash' WHERE Username='$user';";
	if (mysqli_query($con, $sql) == TRUE) {
		$response = TRUE;
	}else{
		$response = FALSE;
		die('Error: ' . mysqli_error($con));

	}
	return $response;


}

function removeLogin($user){
	global $con;
	$respose;	
	$sql  = "DELETE FROM Account where Username = '$user'";
	if (!mysqli_query($con,$sql)){
		die('Error: ' . mysqli_error($con));
		$response = null;
	} else {
		$result = mysqli_fetch_array(mysqli_query($con,$sql));
		$response = $result;
	}
	return $response;	
}
