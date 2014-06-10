<?

	function getHoleDate($tokenw,$beaconID){
		$reply = array();
		session_start();	
		if($_SESSION['token'] == $tokenw){
			$reply['response']  = TRUE;
			global $con;
			$sql  = "SELECT * FROM Hole WHERE BeaconID = '$beaconID'";
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
		    $response['DistanceToPin'] = $row['DistanceToPin'];
			$response['Par'] = $row['Par'];
		    $reply['Data'][] = $response;
			}		
		} else{
			$reply['response']  = 'ERROR';
			$reply['message']  = 'invalid token have you tried to login';
		}
		return json_encode($reply);
	}
	
/*	function getHoleDate($tokenw,$beaconID){
		$reply = array();
		session_start();	
		if($_SESSION['token'] == $tokenw){
			$reply['response']  = TRUE;
			global $con;
			$sql  = "SELECT * FROM Game WHERE BeaconID = '$beaconID'";
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
		    $response['DistanceToPin'] = $row['DistanceToPin'];
			$response['Par'] = $row['Par'];
		    $reply['Data'][] = $response;
			}		
		} else{
			$reply['response']  = 'ERROR';
			$reply['message']  = 'invalid token have you tried to login';
		}
		return json_encode($reply);
	}*/

?>