const unzipper = require('unzipper');
const path = require('path');
const fs = require('fs');

/**
 * Safe: Validates entry paths with path.resolve() before writing streams.
 * Skips entries that would escape destDir instead of throwing.
 */
function extractZipSafe(zipPath, destDir) {
    const resolvedDest = path.resolve(destDir);

    return new Promise((resolve, reject) => {
        fs.createReadStream(zipPath)
            .pipe(unzipper.Parse())
            .on('entry', entry => {
                // Safe: path.resolve eliminates ".." and we verify containment
                const destPath = path.resolve(destDir, entry.path);

                if (!destPath.startsWith(resolvedDest + path.sep)) {
                    // Skip malicious entries rather than writing them
                    entry.autodrain();
                    return;
                }

                if (entry.type === 'Directory') {
                    fs.mkdirSync(destPath, { recursive: true });
                    entry.autodrain();
                } else {
                    fs.mkdirSync(path.dirname(destPath), { recursive: true });
                    entry.pipe(fs.createWriteStream(destPath));
                }
            })
            .on('finish', resolve)
            .on('error', reject);
    });
}

module.exports = { extractZipSafe };
