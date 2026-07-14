<?php
// String concatenation building SQL from user input
function searchUsers($conn, $username) {
    $query = "SELECT * FROM users WHERE username = '" . $username . "'";
    $result = $conn->query($query); // VULNERABLE
    return $result->fetch_all();
}

function getOrder($pdo, $orderId) {
    $sql = "SELECT * FROM orders WHERE id = " . $orderId;
    $stmt = $pdo->query($sql); // VULNERABLE
    return $stmt->fetchAll();
}
