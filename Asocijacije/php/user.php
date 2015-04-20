<?php
 
$response = array();
 
// include db connect class

require_once("db_connect.php");
$db = new db_connect();
$upit="SELECT * FROM korisnik";
$result = $db->selectDB($upit);
// check for empty result
if ($result->num_rows > 0) {
    // looping through all results
    // products node
    $response["users"] = array();
 
    while ($row = $result->fetch_array()) {
        // temp user array
        $user = array();
        $user["id"] = $row["id_korisnika"];
        $user["name"] = $row["ime"];
        $user["surname"] = $row["prezime"];
        $user["username"] = $row["kor_ime"];
        $user["password"] = $row["lozinka"];
        $user["result"] = $row["rezultat"];
        $user["rating"] = $row["rating"];
 
        // push single product into final response array
        array_push($response["users"], $user);
       
    }
    echo json_encode($response);
}
?>