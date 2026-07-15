<?php
// basename() strips directory components — safe for flat extraction
function extractZip($zipPath, $destDir) {
    $zip = new ZipArchive();
    $zip->open($zipPath);

    for ($i = 0; $i < $zip->numFiles; $i++) {
        $entryName = basename($zip->getNameIndex($i)); // SAFE — strips all path components
        if ($entryName === '' || $entryName === '.') {
            continue;
        }
        file_put_contents($destDir . '/' . $entryName, $zip->getFromIndex($i));
    }
    $zip->close();
}
