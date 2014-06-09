<?php
ini_set('display_startup_errors',1);
ini_set('display_errors',1);
error_reporting(-1);


require_once ('settings.php');
require_once('dbconnect.php');
	function testconn(){
			
			global $con;
			$sql  = "SELECT * from test_table";
			$resp;
			if (!mysqli_query($con,$sql)){
				$resp = FALSE;
				die('Error: ' . mysqli_error($con));
			}else{
				$resp = mysqli_query($con,$sql);
			}
			
			$response = array();
			$reply = array();
			
			while($row = mysqli_fetch_array($resp)) {
			  //Add this row to the reply
		    $reply['value'] = $row['id'];
		    $reply['data'] = $row['data'];
		    $response['response'][] = $reply;
			}
			return json_encode($response);
		}


//echo testconn();


?>