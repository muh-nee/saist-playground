<?php
// AES-ECB mode — leaks plaintext patterns
function encryptBlock($data, $key) {
    return openssl_encrypt($data, 'AES-128-ECB', $key); // VULNERABLE — ECB mode reveals structure
}

// Hardcoded static IV — same IV reused across encryptions
function encryptWithStaticIv($data, $key) {
    $iv = str_repeat("\0", 16); // VULNERABLE — hardcoded zero IV
    return openssl_encrypt($data, 'AES-256-CBC', $key, 0, $iv);
}

function encryptMcrypt($data, $key) {
    return mcrypt_encrypt(MCRYPT_DES, $key, $data, MCRYPT_MODE_CBC); // VULNERABLE — deprecated extension + DES
}
