<?php
// Manual extraction without entry path validation
function extractZip($zipPath, $destDir) {
    $zip = new ZipArchive();
    $zip->open($zipPath);
    for ($i = 0; $i < $zip->numFiles; $i++) {
        $entryName = $zip->getNameIndex($i);
        $dest = $destDir . '/' . $entryName;
        file_put_contents($dest, $zip->getFromIndex($i)); // VULNERABLE — ../../ not checked
    }
    $zip->close();
}
