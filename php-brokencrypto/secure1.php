<?php
// AES-256-GCM with random IV — authenticated encryption
function encryptData($data, $key) {
    $ivLen = openssl_cipher_iv_length('AES-256-GCM');
    $iv = openssl_random_pseudo_bytes($ivLen);
    $tag = '';
    $ciphertext = openssl_encrypt($data, 'AES-256-GCM', $key, 0, $iv, $tag); // SAFE
    return base64_encode($iv . $tag . $ciphertext);
}
