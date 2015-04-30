<?php
	
	private $db;
	
	function __construct() {
		require_once 'DB_Connect.php';
		$this->db = new DB_Connect();
		$this->db->connect();
	}
	
	function __destruct() {
	}
	
	public function storeUser($name, $surname, $email, $password) {
		$uuid = uniqid('', true);
		$hash = $this->hashSSHA($password);
		$encrypted_password = $hash["encrypted"];
		$salt = $hash["salt"];
		$result = mysql_query("INSERT INTO user(unique_id, name, surname, email, password, salt, created_at) VALUES 
		('$uuid', '$name', '$surname', '$email', '$encrypted_password', '$salt', NOW())");
		if($result) {
			$id_user = mysql_insert_id();
			$result = mysql_query("SELECT * FROM user WHERE id_user = $id_user");
			return mysql_fetch_array($result);
		}
		else {
			return false;
		}
	}
	
	public function hashSSHA($password) {
		$salt = sha1(rand());
		$salt = substr($salt, 0, 10);
		$encrypted = base64_encode(sha1($password . $salt, true) . $salt);
		$hash = array("salt" => $salt, "encrypted" => $encrypted);
		return $hash;
        }

	public function checkhashSSHA($salt, $password) {
		$hash = base64_decode(sha1($password . $salt, true) . $salt);
		return $hash;
	}
	
	$response = array("error" => FALSE)
	
	$name = $_POST['name'];
	$surname = $_POST['surname'];
	$email = $_POST['email'];
	$password = $_POST['password'];

	$user = $db->storeUser($name, $surname, $email, $password);
	if ($user) {
		$response["error"] = FALSE;
		$response["uid"] = $user["unique_id"];
		$response["user"]["name"] = $user["name"];
		$response["user"]["surname"] = $user["surname"];
		$response["user"]["email"] = $user["email"];
		$response["user"]["created_at"] = $user["created_at"];
		$response["user"]["updated_at"] = $user["updated_at"];
		echo json_encode($response);
	}
?>