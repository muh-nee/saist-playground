<?php
// Range check for pack() — value must fit in 32-bit unsigned
function packData() {
    $size = (int)$_POST['size'];
    if ($size < 0 || $size > 0xFFFFFFFF) {
        http_response_code(400);
        exit;
    }
    return pack('N', $size); // SAFE — value verified to fit the format
}
