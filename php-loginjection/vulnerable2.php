<?php
// Monolog with user input interpolated into message string
function logSearch($logger) {
    $query = $_GET['q'];
    $logger->info("Search executed: " . $query); // VULNERABLE — newlines in message
}

// file_put_contents to log file with unsanitized input
function appendLog() {
    $msg = $_POST['message'];
    $line = date('Y-m-d H:i:s') . " " . $msg . "\n";
    file_put_contents('/var/log/app.log', $line, FILE_APPEND); // VULNERABLE
}
