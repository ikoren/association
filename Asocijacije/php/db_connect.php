
<?php

class db_connect {

 const server = "188.166.115.186";
 const baza = "asocijacije";
 const korisnik = "root";
 const lozinka = "du79me32";
	

     function spojiDB() {
        $mysqli = new mysqli(self::server, self::korisnik, self::lozinka, self::baza);
        if ($mysqli->connect_errno) {
            echo "Neuspjesno spajanje na bazu: " . $mysqli->connect_errno . ", " . $mysqli->connect_error;
        }
        return $mysqli;
    }

    function selectDB($upit) {
        
        $veza = $this->spojiDB();
        $rezultat = $veza->query($upit) or trigger_error("Greška kod upita {$upit} - "
                        . "Greška: " . $veza->error . " " . E_USER_ERROR);
        if (!$rezultat) {
            $rezultat = null;
        }
        $veza->close();
        return $rezultat;
    }
    function updateDB($upit) {
    	$veza = self::spojiDB();
    	if ($rezultat = $veza->query($upit)) {
    		$veza->close();
    		 
    
    		return $rezultat;
    
    	} else {
    		$veza->close();
    		return null;
    	}

   
    }
}

?>