<?php
// Options array without 'secure' key — defaults to false
function setCsrfCookie($token) {
    setcookie('csrf', $token, [
        'httponly' => true,
        'path' => '/',
        'samesite' => 'Strict',
        // VULNERABLE — 'secure' missing, defaults to false
    ]);
}

// HttpOnly does NOT imply Secure
function setRememberMeCookie($token, $expires) {
    setcookie('remember_me', $token, $expires, '/', '', false, true); // VULNERABLE — $secure=false
}
