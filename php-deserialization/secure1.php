<?php
// unserialize with allowed_classes => false — safe for data-only payloads
function loadPreferences() {
    $data = $_COOKIE['prefs'];
    $prefs = unserialize(base64_decode($data), ['allowed_classes' => false]); // SAFE — no objects
    return $prefs;
}

// Better: use json_decode instead
function restoreCart() {
    $cartData = $_POST['cart'];
    $cart = json_decode($cartData, true); // SAFE — JSON cannot instantiate PHP objects
    return $cart;
}
