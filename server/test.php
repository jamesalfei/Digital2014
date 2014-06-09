<?php

ini_set('display_startup_errors',1);
ini_set('display_errors',1);
error_reporting(-1);


require_once ('login.php');

echo addLogin('Robert','test');
echo "<br/>";
echo "<br/>";
echo login('Robert','test');
?>	