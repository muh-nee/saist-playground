<?php
// realpath() + prefix check — canonical path validation
function readFile() {
    $base = realpath('/var/www/uploads');
    $requested = realpath('/var/www/uploads/' . $_GET['file']);

    if ($requested === false || !str_starts_with($requested, $base . DIRECTORY_SEPARATOR)) {
        http_response_code(403);
        exit;
    }
    return file_get_contents($requested); // SAFE — path confirmed within base dir
}
