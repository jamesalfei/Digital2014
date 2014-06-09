<html>
	
	
	
	<form method="post" action="http://eyerange.co.uk/api/check.php">
		<label>Check</label><input type="text" value="token" name="usrToken" id="usrToken"/>
		<input type="submit" />
	</form>
	
	<form method="post" action="http://eyerange.co.uk/api/">
		<label>Login</label>
		<input type="text" name="content_type" value="content_type" id="usrToken"/>
		<input type="text" name="request_type" value="request_type" id="usrToken"/>
		<input type="text" name="username"  value="username" id="usrToken"/>
		<input type="text" name="password" value="password" id="usrToken"/>

<<<<<<< HEAD
		<input type="submit" />
	</form>
	
</html>
=======
ini_set('display_startup_errors',1);
ini_set('display_errors',1);
error_reporting(-1);

require_once ('settings.php');
require_once('dbconnect.php');
require_once ('login.php');

echo addLogin('Robert','test');
echo "<br/>";
echo "<br/>";
echo login('Robert','test');
?>	
>>>>>>> FETCH_HEAD
