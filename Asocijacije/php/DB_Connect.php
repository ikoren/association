<?php

class DB_Connect {
	function __construct() {
	}
	
	function __destruct() {
		//this->close();
	}
	
	public function connect() {
		require_once 'include/Config.php';
		$con = mysql_connect(DB_HOST, DB_USER, DB_PASSWORD) or die(mysql_error());
		mysql_select_db(DB_DATABASE) or die(mysql_error());
		
		return $con;
	}
	
	public function close() {
		mysql_close();
	}
}

?>
