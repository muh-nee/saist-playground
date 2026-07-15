<?php
// User input with newlines written directly to error_log
function logLogin() {
    $username = $_POST['username'];
    error_log("Login attempt for user: " . $username); // VULNERABLE — newlines forge log lines
}

function logRequest() {
    $ip = $_SERVER['REMOTE_ADDR'];
    $path = $_GET['path'];
    error_log("[" . date('Y-m-d') . "] " . $ip . " accessed " . $path); // VULNERABLE
}
