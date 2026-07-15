<?php
// setcookie without Secure flag — transmitted over HTTP
function setSessionCookie($sessionId) {
    setcookie('session', $sessionId); // VULNERABLE — no Secure, no HttpOnly
}

function setAuthCookie($token) {
    setcookie('auth_token', $token, 0, '/', ''); // VULNERABLE — Secure=false (positional default)
}
