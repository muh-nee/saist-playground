<?php
// shell_exec and backtick operator with user input
function getDiskUsage($path) {
    $dir = $_GET['dir'];
    return shell_exec("du -sh " . $dir); // VULNERABLE
}

function runScript($name) {
    $script = $_REQUEST['script'];
    $result = `bash /scripts/$script`; // VULNERABLE — backtick operator
    return $result;
}

function compress($file) {
    $filename = $_POST['file'];
    passthru("gzip " . $filename); // VULNERABLE
}
