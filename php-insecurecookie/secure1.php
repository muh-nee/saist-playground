<?php
// Options array with secure => true
function setSessionCookie($sessionId) {
    setcookie('session', $sessionId, [
        'secure'   => true,
        'httponly' => true,
        'samesite' => 'Strict',
        'path'     => '/',
    ]); // SAFE
}

function setCsrfCookie($token) {
    setcookie('csrf', $token, [
        'secure'   => true,
        'httponly' => true,
        'samesite' => 'Strict',
        'path'     => '/',
    ]); // SAFE
}
