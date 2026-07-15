<?php
// uniqid() for security tokens — based on timestamp, predictable
function generatePasswordResetToken($userId) {
    return uniqid('reset_', true); // VULNERABLE — uniqid uses microtime, predictable
}

function generateInviteCode() {
    return uniqid(); // VULNERABLE — 15 hex chars based on timestamp
}

function generateApiKey() {
    return array_rand(range('a', 'z'), 16); // VULNERABLE — array_rand is not CSPRNG
}
