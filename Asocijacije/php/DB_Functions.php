<?php
	class DB_Functions {
		private $db;
		
		function __construct() {
			require_once 'DB_Connect.php';
			$this->db = new DB_Connect();
			$this->db->connect();
		}
		
		function __destruct() {
		}

        /**
         * @param $name
         * @param $surname
         * @param $email
         * @param $password
         * @return array|bool
         * funkcija za pohranu korisnika u bazu podataka
         */
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

        /**
         * @param $email
         * @param $password
         * @return array|bool|resource
         * funkcija za pretraživanje korisnika po emailu i šifri
         */
		public function getUserByEmailAndPassword($email, $password) {
			$result = mysql_query("SELECT * FROM user WHERE email = '$email'") or die(mysql_error());
			$no_rows = mysql_num_rows($result);
			if ($no_rows > 0) {
				$result = mysql_fetch_array($result);
				$salt = $result['salt'];
				$encrypted_password = $result['encrypted_password'];
				$hash = $this->checkhashSSHA($salt, $password);
				if ($encrypted_password == $hash) {
					return $result;
				}
			}
			else {
				return false;
			}
		}

        /**
         * @param $email
         * @return bool
         * funkcija za utvrđivanje egzistencije korisnika
         */
        public function isUserExisted($email) {
            $result = mysql_query("SELECT * FROM user WHERE email = '$email'");
            $no_rows = mysql_num_rows($result);
            if ($no_rows > 0) {
                return true;
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
	}
?>