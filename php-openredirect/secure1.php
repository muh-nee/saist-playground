<?php
// Allowlist of permitted redirect targets
function redirect() {
    $allowed = ['https://example.com/dashboard', 'https://example.com/profile'];
    $url = $_GET['redirect'];
    if (!in_array($url, $allowed, true)) {
        $url = '/dashboard'; // Default safe redirect
    }
    header("Location: " . $url); // SAFE — only allowlisted URLs
    exit;
}
