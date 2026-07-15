<?php
// fopen and file_put_contents with user-controlled paths
function downloadFile() {
    $filename = $_GET['name'];
    $handle = fopen("/var/www/files/" . $filename, 'r'); // VULNERABLE
    fpassthru($handle);
    fclose($handle);
}

function saveUpload() {
    $dest = $_POST['destination'];
    $data = file_get_contents('php://input');
    file_put_contents("/uploads/" . $dest, $data); // VULNERABLE
}
