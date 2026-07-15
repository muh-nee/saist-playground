<?php
// Host validation — only same-host redirects allowed
function redirect() {
    $url = $_GET['redirect'];
    $parsed = parse_url($url);

    // Reject if host is set and differs from our domain
    if (!empty($parsed['host']) && $parsed['host'] !== 'example.com') {
        http_response_code(400);
        exit;
    }
    header("Location: " . $url); // SAFE — off-host redirects blocked
    exit;
}

// WordPress: use wp_safe_redirect() instead of wp_redirect()
function wpSafeRedirect() {
    $dest = $_GET['destination'];
    wp_safe_redirect($dest); // SAFE — validates host against allowed list
    exit;
}
