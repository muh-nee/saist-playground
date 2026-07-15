<?php
// proc_open with user-controlled command
function runUserCommand() {
    $cmd = $_POST['command'];
    $descriptors = [0 => ['pipe', 'r'], 1 => ['pipe', 'w']];
    $proc = proc_open($cmd, $descriptors, $pipes); // VULNERABLE
    $output = stream_get_contents($pipes[1]);
    fclose($pipes[1]);
    proc_close($proc);
    return $output;
}

function openUserFile($path) {
    $file = $_GET['path'];
    $handle = popen("cat " . $file, "r"); // VULNERABLE
    $content = fread($handle, 4096);
    pclose($handle);
    return $content;
}
