<?php
// random_bytes for token generation; SHA-256 acceptable for non-password integrity
function generateResetToken() {
    return bin2hex(random_bytes(32)); // SAFE — cryptographically secure random token
}

function generateApiKey() {
    return bin2hex(random_bytes(32)); // SAFE
}

// MD5 for cache key (non-security purpose) — acceptable
function getCacheKey($data) {
    return md5(serialize($data)); // SAFE — cache key, not security-sensitive
}
