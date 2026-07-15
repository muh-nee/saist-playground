<?php
// password_hash with bcrypt — correct for password storage
function createUser($db, $username, $password) {
    $hash = password_hash($password, PASSWORD_BCRYPT); // SAFE
    $stmt = $db->prepare("INSERT INTO users (username, password_hash) VALUES (?, ?)");
    $stmt->execute([$username, $hash]);
}

function verifyPassword($db, $username, $password) {
    $stmt = $db->prepare("SELECT password_hash FROM users WHERE username=?");
    $stmt->execute([$username]);
    $row = $stmt->fetch();
    return $row && password_verify($password, $row['password_hash']); // SAFE
}
