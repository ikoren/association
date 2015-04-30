<?php

var_dump($_POST);

    if (isset($_POST['tag']) && $_POST['tag'] != ''){
        $tag = $_POST['tag'];

        require_once 'include/DB_Functions.php';
        $db = new DB_Functions();

        $response = array("tag" => $tag, "error" => FALSE);

        if ($tag == 'login') {
            $email = $_POST['email'];
            $password = $_POST['password'];

            $user = $db->getUserByEmailAndPassword($email, $password);
            if ($user != false) {
                $response["error"] = FALSE;
                $response["uid"] = $user["unique_id"];
                $response["user"]["name"] = $user["name"];
                $response["user"]["surname"] = $user["surname"];
                $response["user"]["email"] = $user["email"];
                $response["user"]["created_at"] = $user["created_at"];
                $response["user"]["updated_at"] = $user["updated_at"];
                echo json_encode($response);
            }
            else {
                $response["error"] = TRUE;
                $response["error_msg"] = "Incorrect email or password!";
                echo json_encode($response);
            }
        }
        else if ($tag == 'register') {
            $name = $_POST['name'];
            $surname = $_POST['surname'];
            $email = $_POST['email'];
            $password = $_POST['password'];

            if ($db->isUserExisted($email)) {
                $response["error"] = TRUE;
                $response["error_msg"] = "User already existed";
                echo json_encode($response);
            }
            else {
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
                else {
                    $response["error"] = TRUE;
                    $response["error_msg"] = "Error occured in registration";
                    echo json_encode($response);
                }
            }
        }
        else {
            $response["error"] = TRUE;
            $response["error_msg"] = "Unknown 'tag' value. It should be either 'login' or 'register'.";
            echo json_encode($response);
        }
    }
    else {
        $response["error"] = TRUE;
        $response["error_msg"] = "Required parameter 'tag' is missing.";
        echo json_encode($response);
    }
?>
