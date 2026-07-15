<?php
// Positional syntax with $secure=true (6th argument)
function setAuthCookie($token, $expires) {
    setcookie('auth_token', $token, $expires, '/', '', true, true); // SAFE — $secure=true, $httponly=true
}

function setRememberMeCookie($token, $expires) {
    setcookie('remember_me', $token, $expires, '/', 'example.com', true, true); // SAFE
}
