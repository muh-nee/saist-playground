<?php
// MySQLi prepared statements with bind_param
function getUserByUsername($conn, $username) {
    $stmt = $conn->prepare("SELECT * FROM users WHERE username = ?");
    $stmt->bind_param("s", $username);
    $stmt->execute(); // SAFE
    return $stmt->get_result()->fetch_assoc();
}

function getProductById($conn, $id) {
    $stmt = $conn->prepare("SELECT * FROM products WHERE id = ?");
    $stmt->bind_param("i", $id);
    $stmt->execute(); // SAFE
    return $stmt->get_result()->fetch_assoc();
}
