<?php
// User-controlled filename passed to file_get_contents
function readFile() {
    $file = $_GET['file'];
    return file_get_contents("/var/www/uploads/" . $file); // VULNERABLE — path traversal via ../
}

function getTemplate() {
    $name = $_GET['template'];
    return file_get_contents("templates/" . $name . ".html"); // VULNERABLE
}
