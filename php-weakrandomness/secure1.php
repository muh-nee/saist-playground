<?php
// random_bytes() — cryptographically secure
function generateSessionToken() {
    return bin2hex(random_bytes(32)); // SAFE — CSPRNG
}

function generateCsrfToken() {
    return bin2hex(random_bytes(32)); // SAFE
}

function generateOtp() {
    return str_pad((string)random_int(0, 999999), 6, '0', STR_PAD_LEFT); // SAFE
}
