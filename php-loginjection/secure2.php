<?php
// Structured logging — user input in context array, not message string
function logSearch($logger) {
    $logger->info('Search executed', [
        'query' => $_GET['q'],   // SAFE — Monolog encodes context values
        'ip'    => $_SERVER['REMOTE_ADDR'],
    ]);
}

function logLogin($logger) {
    $logger->info('Login attempt', [
        'user' => $_POST['username'], // SAFE — structured field, not interpolated
    ]);
}
