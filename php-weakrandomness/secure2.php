<?php
// random_int() for numeric ranges — CSPRNG
function generatePasswordResetToken() {
    return bin2hex(random_bytes(32)); // SAFE — not predictable
}

function generateApiKey() {
    return bin2hex(random_bytes(32)); // SAFE

}

// mt_rand for non-security purpose — acceptable
function randomizeDisplayOrder(array $items) {
    shuffle($items); // SAFE — display ordering, not security-sensitive
    return $items;
}
