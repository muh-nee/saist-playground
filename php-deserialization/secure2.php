<?php
// HMAC signature verification before deserialization
function loadSession() {
    $payload = $_COOKIE['session'];
    [$hmac, $data] = explode('.', $payload, 2);

    $expected = hash_hmac('sha256', $data, SECRET_KEY);
    if (!hash_equals($expected, $hmac)) {
        http_response_code(403);
        exit;
    }
    return unserialize(base64_decode($data)); // SAFE — integrity verified before deserializing
}
