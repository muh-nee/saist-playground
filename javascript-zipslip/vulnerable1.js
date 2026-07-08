const AdmZip = require('adm-zip');
const path = require('path');
const fs = require('fs');

/**
 * VULNERABLE: Extracts a zip archive without validating entry paths.
 * entry.entryName may contain "../" sequences that escape destDir.
 */
function extractZip(zipPath, destDir) {
    const zip = new AdmZip(zipPath);
    zip.getEntries().forEach(entry => {
        // VULNERABLE: path.join does not sanitize "../" in entry.entryName
        const destPath = path.join(destDir, entry.entryName);

        if (entry.isDirectory) {
            fs.mkdirSync(destPath, { recursive: true });
        } else {
            fs.mkdirSync(path.dirname(destPath), { recursive: true });
            fs.writeFileSync(destPath, entry.getData());
        }
    });
}

module.exports = { extractZip };
