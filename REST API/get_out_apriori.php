<?php
	$host='srv70.hosting24.com';
	$uname='heartfit_root';
	$pwd='heart123';
	$db="heartfit_123";

	$con = mysql_connect($host,$uname,$pwd) or die("connection failed");
	mysql_select_db($db,$con) or die("db selection failed");
	 
	$out_date=$_REQUEST['out_date'];
	
	$retval = mysql_query("select out_hr,out_num,out_hour from output_apriori where out_date='$out_date'",$con);
   
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