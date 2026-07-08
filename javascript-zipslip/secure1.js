const AdmZip = require('adm-zip');
const path = require('path');
const fs = require('fs');

/**
 * Safe: Extracts a zip archive with path.resolve() validation.
 * Verifies each entry resolves within destDir before writing.
 */
function extractZipSafe(zipPath, destDir) {
    const resolvedDest = path.resolve(destDir);

    const zip = new AdmZip(zipPath);
    zip.getEntries().forEach(entry => {
        // Safe: path.resolve eliminates ".." and we verify containment
        const destPath = path.resolve(destDir, entry.entryName);

        if (!destPath.startsWith(resolvedDest + path.sep)) {
            throw new Error(`Illegal entry: ${entry.entryName}`);
        }

        if (entry.isDirectory) {
            fs.mkdirSync(destPath, { recursive: true });
        } else {
            fs.mkdirSync(path.dirname(destPath), { recursive: true });
            fs.writeFileSync(destPath, entry.getData());
        }
    });
}

module.exports = { extractZipSafe };
