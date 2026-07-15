<?php
// unserialize() on user-controlled cookie data — POP chain risk
function loadSession() {
    $data = $_COOKIE['session'];
    $obj = unserialize(base64_decode($data)); // VULNERABLE — attacker-controlled object graph
    return $obj;
}

function restoreCart() {
    $cartData = $_POST['cart'];
    $cart = unserialize($cartData); // VULNERABLE
    return $cart;
}
