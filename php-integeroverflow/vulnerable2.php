<?php
// pack() with user-controlled value — silent truncation for large ints
function packData() {
    $size = (int)$_POST['size'];
    $packed = pack('N', $size); // VULNERABLE — N is 32-bit; values > 0xFFFFFFFF truncate silently
    return $packed;
}

// array_fill with unchecked user count
function buildArray() {
    $num = (int)$_GET['num'];
    return array_fill(0, $num, null); // VULNERABLE — large $num exhausts memory
}
