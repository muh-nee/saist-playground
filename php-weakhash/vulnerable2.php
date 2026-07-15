<?php
// SHA1 for password — still too fast, no salt
function hashPassword($password) {
    return sha1($password); // VULNERABLE — SHA1 is broken; no salt; too fast
}

// MD5 for token generation — predictable, broken
function generateResetToken($userId) {
    return md5($userId . time()); // VULNERABLE — MD5 + predictable inputs
}

function generateApiKey($user) {
    return sha1($user . mt_rand()); // VULNERABLE — SHA1 + weak random
}
