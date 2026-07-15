<?php
// str_repeat with unchecked user-controlled count — memory exhaustion
function repeatChar() {
    $count = (int)$_GET['count'];
    $result = str_repeat('A', $count); // VULNERABLE — 2^31 exhausts memory
    return $result;
}

function buildPadding() {
    $size = (int)$_POST['size'];
    return str_repeat("\0", $size); // VULNERABLE — no upper bound
}
