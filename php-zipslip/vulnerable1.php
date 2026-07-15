<?php
// ZipArchive::extractTo — no per-entry path validation
function extractUpload($zipPath, $destDir) {
    $zip = new ZipArchive();
    if ($zip->open($zipPath) === true) {
        $zip->extractTo($destDir); // VULNERABLE — entry names like ../../etc/passwd extracted
        $zip->close();
    }
}
