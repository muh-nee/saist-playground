const unzipper = require('unzipper');
const path = require('path');
const fs = require('fs');

/**
 * VULNERABLE: Pipes unzipper entries to writeStream without path validation.
 * entry.path may contain "../" sequences that escape destDir.
 */
function extractZip(zipPath, destDir) {
    fs.createReadStream(zipPath)
        .pipe(unzipper.Parse())
        .on('entry', entry => {
            // VULNERABLE: path.join does not sanitize "../" in entry.path
            const destPath = path.join(destDir, entry.path);

            if (entry.type === 'Directory') {
                fs.mkdirSync(destPath, { recursive: true });
                entry.autodrain();
            } else {
                fs.mkdirSync(path.dirname(destPath), { recursive: true });
                entry.pipe(fs.createWriteStream(destPath));
            }
        });
}

module.exports = { extractZip };
