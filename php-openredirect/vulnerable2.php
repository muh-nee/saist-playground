<?php
// Naive prefix check — bypassed by protocol-relative URLs
function redirect() {
    $url = $_GET['url'];
    // VULNERABLE — attacker can use //evil.com which starts with /
    if (strpos($url, 'http://') === 0 || strpos($url, 'https://') === 0) {
        header("Location: " . $url);
        exit;
    }
    header("Location: " . $url); // VULNERABLE — protocol-relative //evil.com bypasses above
    exit;
}

// WordPress wp_redirect without validation
function wpRedirect() {
    $dest = $_GET['destination'];
    wp_redirect($dest); // VULNERABLE — unvalidated external URL
    exit;
}
