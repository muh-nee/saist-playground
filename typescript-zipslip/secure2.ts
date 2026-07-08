import * as unzipper from 'unzipper';
import * as path from 'path';
import * as fs from 'fs';

/**
 * Safe: Validates entry paths at runtime with path.resolve() before piping.
 * Skips entries that would escape destDir — runtime validation, not type annotations.
 */
function extractZipSafe(zipPath: string, destDir: string): Promise<void> {
    const resolvedDest: string = path.resolve(destDir);

    return new Promise((resolve, reject) => {
        fs.createReadStream(zipPath)
            .pipe(unzipper.Parse())
            .on('entry', (entry: unzipper.Entry) => {
                // Safe: runtime path.resolve check — TypeScript types alone are insufficient
                const destPath: string = path.resolve(destDir, entry.path);

                if (!destPath.startsWith(resolvedDest + path.sep)) {
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

export { extractZipSafe };
