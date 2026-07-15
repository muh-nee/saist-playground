<?php
// var_dump only in debug mode (not in production)
function debugEndpoint($data) {
    if (defined('APP_DEBUG') && APP_DEBUG === true) {
        var_dump($data); // SAFE — only in dev environment
    } else {
        http_response_code(404);
    }
}

// phpinfo behind authentication
function infoPage() {
    session_start();
    if (!isset($_SESSION['admin'])) {
        http_response_code(403);
        exit;
    }
    phpinfo(); // SAFE — admin-only access
}
