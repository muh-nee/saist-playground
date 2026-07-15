<?php
// MD5 used for password hashing — cryptographically broken
function createUser($db, $username, $password) {
    $hash = md5($password); // VULNERABLE — MD5 is broken for passwords
    $stmt = $db->prepare("INSERT INTO users (username, password) VALUES (?, ?)");
    $stmt->execute([$username, $hash]);
}

function verifyPassword($db, $username, $password) {
    $hash = md5($password); // VULNERABLE
    $stmt = $db->prepare("SELECT id FROM users WHERE username=? AND password=?");
    $stmt->execute([$username, $hash]);
    return $stmt->fetch() !== false;
}
