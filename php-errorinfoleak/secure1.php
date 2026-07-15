<?php
// Log internally, return generic message to client
function processPayment($db, $amount) {
    try {
        $db->exec("INSERT INTO payments (amount) VALUES (?)");
    } catch (PDOException $e) {
        error_log($e->getMessage()); // SAFE — logged server-side only
        http_response_code(500);
        echo json_encode(['error' => 'Payment processing failed']); // generic message
    }
}
