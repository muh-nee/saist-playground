<?php
// Strip newlines before logging
function logLogin() {
    $username = preg_replace('/[\r\n]/', '', $_POST['username']);
    error_log("Login attempt for user: " . $username); // SAFE — newlines removed
}

function logRequest() {
    $path = preg_replace('/[\r\n\0]/', '', $_GET['path']);
    error_log("[" . date('Y-m-d') . "] accessed " . $path); // SAFE
}
