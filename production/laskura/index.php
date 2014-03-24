<?php
// Laskura
// A Minimum Working Example of the ServerIO capabilities of Cutlass.
// Gaurav Manek

define("DEBUG", false);

include("incl/OBJ_mysql.php");
global $db;

//
// Configuration:
//

$db_config = array();
$db_config["hostname"]  = "mysql.dev.gauravmanek.com";
$db_config["database"]  = "laskura";
$db_config["username"]  = "laskura_user";
$db_config["password"]  = "v73kyWYxcXy7uyTgwqGynW76";

$db = new OBJ_mysql($db_config);
$request = array();
if(DEBUG){
	$request = $_GET;
} else {
	if(!isset($_REQUEST['json'])){
		echo "<pre>".file_get_contents("README")."</pre>";
		exit();
	}
	$request = json_decode($_REQUEST['json'], true); // Decode to assoc array
}

if(!isset($request["action"])){
	output_failure("Parameter 'action' not defined!");
	exit();
}

if($request['action'] == "LOGIN"){
	if(!isset($request["username"]) || !isset($request["password"])){
		output_failure("Parameter 'username' or 'password' not defined!");
	} else {
		output(handleLogin($request['username'], $request['password']));
	}
} else {
	$user_session = user_auth($request['sessionid']);
	if($user_session["success"] == true){
		switch($request['action']){
			case "LOGOUT":
				output(handleLogout($user_session));
				break;
			case "LIST":
				output(handleList($user_session));
				break;
			case "READ":
				output(handleRead($user_session, $request));
				break;
			case "WRITE":
				output(handleWrite($user_session, $request));
				break;
			case "CREATE_IF_NOT_EXISTS":
				output(handleCINE($user_session, $request));
				break;
			case "SAVE_AND_EXECUTE":
				output(handleSAE($user_session, $request));
				break;
			default:
				output_failure("Parameter 'action' incorrect!");
		}
	} else {
		output_failure("Session token invalid.");
	}
}

function handleLogin($user, $pw){
	global $db;
	$rv = array();
	$login = $db->query("SELECT * FROM users WHERE username = ? AND password = PASSWORD(?)", array($user, $pw));
	
	if($login && $userrow = $login->fetchArray()) {
		$rv["success"] = true;
		// Generate a new sessionid, deleting the old one if it is still there.
		$db->delete("session", array("user_id" => $userrow['id']));
		$rv["session_id"] = $db->insert("session", array("user_id" => $userrow['id']));
	} else {
		$rv["success"] = false;
		$rv["error"] = "The username/password combination could not be found.";
	}
	return $rv;
}

function handleLogout($svar){
	global $db;
	$db->delete("session", array("session_id" => $svar['session_id']));
	$rv["success"] = true;
	return $rv;
}


function handleList($svar){
	global $db;
	$flist = $db->query("SELECT filename FROM data WHERE user_id = ? ORDER BY updated DESC", array($svar["user_id"]));
	$rv["success"] = true;
	$rv["files"] = $flist?$flist->fetchAllArray():array();
	return $rv;
}

function handleRead($svar, $rq){
	global $db;
	if(!isset($rq['filename'])){
		return failure("No filename given.");
	}
	
	$fdat = $db->query("SELECT * FROM data WHERE user_id = ? AND filename = ?", array($svar["user_id"], $rq['filename']));
	if($fdat){
		$datarow = $fdat->fetchArray();
		$rv["success"] = true;
		$rv["data"] = $datarow["data"];
		return $rv;
	} else {
		return failure("File not found.");
	}
}

// This function returns success if the target file cannot be overwritten.
function handleWrite($svar, $rq){
	global $db;
	if(!isset($rq['filename'])){
		return failure("No filename given.");
	}
	
	$fdat = $db->update("data", array("data" => $rq["data"]), array("user_id" => $svar["user_id"], "filename" => $rq['filename']));
	if($fdat){
		$rv["success"] = true;
		return $rv;
	} else {
		return failure("File not written to.");
	}
}

function handleCINE($svar, $rq){
	global $db;
	if(!isset($rq['filename'])){
		return failure("No filename given.");
	}
	
	$fdat = $db->query("INSERT IGNORE INTO data SET data = '', user_id = ?, filename = ?;", array($svar["user_id"], $rq['filename']));
	if($fdat){
		$rv["success"] = true;
		return $rv;
	} else {
		return failure("File not created.");
	}
}

function handleSAE($svar, $rq){
	return failure("UnsupportedOperationException");
}


// Security function
function user_auth($session){
	global $db;
	$rv = array();
	$login = $db->query("SELECT * FROM session WHERE session_id=?", array($session));
	if($login){
		$row = $login->fetchArray();
		$rv["success"] = true;
		$rv["user_id"] = $row["user_id"];
		$rv["session_id"] = $session;
	} else {
		$rv["success"] = false;
		$rv["error"] = "Invalid session token!";
	}
	return $rv;
}

// Output $data as JSON
function output($data){
	if(!isset($data["success"])){
		die("success not set!");
	}
	echo json_encode($data);
}
function output_failure($msg){
	echo json_encode(failure($msg));
}
function failure($msg){
	return array("success" => false, "error" => $msg);
}

/*
ob_start();
	var_dump($db->log());
	file_put_contents("db.log", ob_get_contents());
ob_end_clean();
*/
?>