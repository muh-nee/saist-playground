<?php
// Exception message and stack trace echoed to HTTP response
function processPayment($db, $amount) {
    try {
        $db->exec("INSERT INTO payments (amount) VALUES ($amount)");
    } catch (PDOException $e) {
        echo $e->getMessage(); // VULNERABLE — exposes SQL and internal paths
    }
}

function loadUser($db, $id) {
    try {
        return $db->query("SELECT * FROM users WHERE id = $id")->fetch();
    } catch (Exception $e) {
        echo $e->getTraceAsString(); // VULNERABLE — full stack trace to client
    }
}
