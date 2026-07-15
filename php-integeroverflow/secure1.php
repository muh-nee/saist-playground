<?php
// Explicit range check before memory-allocation functions
function repeatChar() {
    $count = (int)$_GET['count'];
    if ($count < 0 || $count > 10000) {
        http_response_code(400);
        exit;
    }
    return str_repeat('A', $count); // SAFE — bounded
}

function buildArray() {
    $num = (int)$_GET['num'];
    if ($num < 0 || $num > 1000) {
        http_response_code(400);
        exit;
    }
    return array_fill(0, $num, null); // SAFE — bounded
}
