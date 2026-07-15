<?php
// Fixed command — no user input in command string
function getDiskUsage() {
    $allowedDirs = ['/var/www', '/tmp', '/home'];
    $dir = $_GET['dir'];
    if (!in_array($dir, $allowedDirs, true)) {
        http_response_code(400);
        exit;
    }
    return shell_exec("du -sh " . escapeshellarg($dir)); // SAFE — allowlisted and escaped
}

function resizeImage() {
    // No user input in command; filename from internal logic only
    $tmpFile = tempnam(sys_get_temp_dir(), 'img');
    exec("convert input.jpg -resize 800x600 " . escapeshellarg($tmpFile)); // SAFE
    return $tmpFile;
}
