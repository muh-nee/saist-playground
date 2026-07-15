<?php
// Per-entry realpath check — canonical path must stay within destDir
function extractZip($zipPath, $destDir) {
    $destDir = realpath($destDir);
    $zip = new ZipArchive();
    $zip->open($zipPath);

    for ($i = 0; $i < $zip->numFiles; $i++) {
        $entryName = $zip->getNameIndex($i);
        $entryPath = realpath($destDir . '/' . $entryName);

        if ($entryPath === false || !str_starts_with($entryPath, $destDir . DIRECTORY_SEPARATOR)) {
            continue; // SAFE — skip entries that escape the destination
        }
        file_put_contents($entryPath, $zip->getFromIndex($i));
    }
    $zip->close();
}
