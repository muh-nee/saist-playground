<?php
// User input passed directly to exec() — classic command injection
function ping($host) {
    $output = [];
    exec("ping -c 4 " . $_GET['host'], $output); // VULNERABLE
    return implode("\n", $output);
}

function convertFile($filename) {
    $file = $_POST['filename'];
    system("convert " . $file . " output.png"); // VULNERABLE
    return "done";
}
