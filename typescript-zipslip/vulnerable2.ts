import * as unzipper from 'unzipper';
import * as path from 'path';
import * as fs from 'fs';

/**
 * VULNERABLE: Pipes unzipper entries without validating paths.
 * `entry: unzipper.Entry` TypeScript type does NOT validate path content at runtime.
 */
function extractZip(zipPath: string, destDir: string): void {
    fs.createReadStream(zipPath)
        .pipe(unzipper.Parse())
        .on('entry', (entry: unzipper.Entry) => {
            // VULNERABLE: path.join does not sanitize "../" in entry.path
            // TypeScript type does not prevent runtime path traversal
            const destPath: string = path.join(destDir, entry.path as string);

            if (entry.type === 'Directory') {
                fs.mkdirSync(destPath, { recursive: true });
                entry.autodrain();
            } else {
                fs.mkdirSync(path.dirname(destPath), { recursive: true });
                entry.pipe(fs.createWriteStream(destPath));
            }
        });
}

export { extractZip };
