<?php
	$host='srv70.hosting24.com';
	$uname='heartfit_root';
	$pwd='heart123';
	$db="heartfit_123";

	$con = mysql_connect($host,$uname,$pwd) or die("connection failed");
	mysql_select_db($db,$con) or die("db selection failed");
	 
	$heart_rate=$_REQUEST['heart_rate'];
	$date=$_REQUEST['date'];
	$hour=$_REQUEST['hour'];
	$flag['code']=0;


$retval = mysql_query("insert into heart_rate ( heart_rate , date,hour ) values ('$heart_rate','$date','$hour')",$con);
   
   if(! $retval ) {
      die('Could not get data: ' . mysql_error());
   }
 
	if($retval) {
      echo "success ";
   }
   else {
	   echo "failure";
   }
	echo "how are you?";
	mysql_close($con);
?>