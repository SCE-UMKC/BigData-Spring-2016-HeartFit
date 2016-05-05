<?php
	$host='srv70.hosting24.com';
	$uname='heartfit_root';
	$pwd='heart123';
	$db="heartfit_123";

	$con = mysql_connect($host,$uname,$pwd) or die("connection failed");
	mysql_select_db($db,$con) or die("db selection failed");
	 
	$out_patt=$_REQUEST['out_pattern'];
	
	$retval = mysql_query("SELECT shrt_desc,n_factor,pro_factor,fat_factor,cho_factor,ndb_no 
FROM  foodDescs
WHERE  long_desc LIKE  '%$out_patt%'
LIMIT 0 , 10 ",$con);
   
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