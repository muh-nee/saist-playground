<?php
// rand() / mt_rand() for security tokens — predictable PRNG
function generateSessionToken() {
    return md5(rand()); // VULNERABLE — rand() is not cryptographically secure
}

function generateCsrfToken() {
    return bin2hex(pack('N', mt_rand())); // VULNERABLE — mt_rand() is predictable
}

function generateOtp() {
    return str_pad((string)rand(0, 999999), 6, '0', STR_PAD_LEFT); // VULNERABLE
}
