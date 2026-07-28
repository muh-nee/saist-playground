<?php
// String interpolation directly into query — most common PHP SQLi pattern
function getUserById($conn, $id) {
    $result = $conn->query("SELECT * FROM users WHERE id = $id"); 
    return $result->fetch_assoc();
}

function getProductByName($conn, $name) {
    $result = $conn->query("SELECT * FROM products WHERE name = '$name'"); 
    return $result->fetch_assoc();
}
