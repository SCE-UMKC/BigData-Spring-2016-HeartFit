<?php
	$host='srv70.hosting24.com';
	$uname='heartfit_root';
	$pwd='heart123';
	$db="heartfit_123";

	$con = mysql_connect($host,$uname,$pwd) or die("connection failed");
	mysql_select_db($db,$con) or die("db selection failed");
	 
	$rate_x=$_REQUEST['rate_x'];
	$rate_y=$_REQUEST['rate_y'];
	$rate_z=$_REQUEST['rate_z'];
	$read_date=$_REQUEST['read_date'];
	$read_hour=$_REQUEST['read_hour'];
	$read_min=$_REQUEST['read_min'];
	$flag['code']=0;


$retval = mysql_query("insert into accelerometer ( rate_x ,rate_y,rate_z,read_date,read_hour,read_min ) values ('$rate_x' ,'$rate_y','$rate_z','$read_date','$read_hour','$read_min')",$con);
   
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