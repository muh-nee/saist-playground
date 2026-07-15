<?php
// libsodium — preferred for new code
function encryptData($data, $key) {
    $nonce = random_bytes(SODIUM_CRYPTO_SECRETBOX_NONCEBYTES);
    $ciphertext = sodium_crypto_secretbox($data, $nonce, $key); // SAFE — authenticated encryption
    return base64_encode($nonce . $ciphertext);
}

// AES-256-CBC with random IV — acceptable alternative
function encryptCbc($data, $key) {
    $iv = openssl_random_pseudo_bytes(16);
    $ciphertext = openssl_encrypt($data, 'AES-256-CBC', $key, 0, $iv); // SAFE — strong cipher, random IV
    return base64_encode($iv . $ciphertext);
}
