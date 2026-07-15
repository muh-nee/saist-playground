<?php
// escapeshellarg() sanitizes individual arguments
function ping($host) {
    $safeHost = escapeshellarg($_GET['host']);
    $output = [];
    exec("ping -c 4 " . $safeHost, $output); // SAFE — argument escaped
    return implode("\n", $output);
}

function convertFile() {
    $file = escapeshellarg($_POST['filename']);
    system("convert " . $file . " output.png"); // SAFE
}
