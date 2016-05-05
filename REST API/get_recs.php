<?php
	$host='srv70.hosting24.com';
	$uname='heartfit_root';
	$pwd='heart123';
	$db="heartfit_123";

	$con = mysql_connect($host,$uname,$pwd) or die("connection failed");
	mysql_select_db($db,$con) or die("db selection failed");
	 
	$out_nbid=$_REQUEST['out_nbid'];
	
	$retval = mysql_query("SELECT a.shrt_desc,a.n_factor,a.pro_factor,a.fat_factor,a.cho_factor,a.id 
FROM  foodDescs a, Similarity b
WHERE  b.i1 = '$out_nbid' and b.i2 = a.ndb_no
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