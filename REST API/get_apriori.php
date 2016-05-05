<?php
	$host='mysql8.000webhost.com';
	$uname='a9597336_heart';
	$pwd='heart123';
	$db="a9597336_heart";

	$con = mysql_connect($host,$uname,$pwd) or die("connection failed");
	mysql_select_db($db,$con) or die("db selection failed");
	 

	$retval = mysql_query("select * from apriori",$con);
   
   if(! $retval ) {
      die('Could not get data: ' . mysql_error());
   }
	
	$rows = array();
	while($row = mysql_fetch_array($retval, MYSQL_ASSOC)) {
      //echo "EMP ID :{$row['Age']} ";
		$rows[] = $row;
	}
	print (json_encode($rows));
	mysql_close($con);
?>