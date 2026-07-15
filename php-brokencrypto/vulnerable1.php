<?php
// DES and RC4 — broken algorithms
function encryptData($data, $key) {
    return openssl_encrypt($data, 'DES-CBC', $key, 0, '12345678'); // VULNERABLE — DES is broken
}

function encryptLegacy($data, $key) {
    return openssl_encrypt($data, 'RC4', $key); // VULNERABLE — RC4 stream cipher is broken
}

function encrypt3Des($data, $key) {
    return openssl_encrypt($data, 'DES-EDE3-CBC', $key, 0, '12345678'); // VULNERABLE — 3DES deprecated
}
