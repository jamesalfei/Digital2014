<?
ini_set('display_startup_errors',1);
ini_set('display_errors',1);
error_reporting(-1);

	require_once ('settings.php');
	require_once('dbconnect.php');
	
	function check($tokenw){
		session_start();
		$reply = array();
		if($_SESSION['token'] == $tokenw){
			$reply['response']  = TRUE;
		} else{
			$reply['response']  = 'boo';
		}
		
		return json_encode($reply);	
	}
	
	$usrToken = $_POST['usrToken'];
	echo check($usrToken);
?>