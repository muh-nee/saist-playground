import AdmZip from 'adm-zip';
import * as path from 'path';
import * as fs from 'fs';

/**
 * VULNERABLE: Extracts a zip archive without validating entry paths.
 * TypeScript type AdmZip.IZipEntry does NOT sanitize entry names at runtime.
 */
function extractZip(zipPath: string, destDir: string): void {
    const zip = new AdmZip(zipPath);
    zip.getEntries().forEach((entry: AdmZip.IZipEntry) => {
        // VULNERABLE: path.join does not sanitize "../" in entry.entryName
        // TypeScript type annotation provides no runtime path validation
        const destPath: string = path.join(destDir, entry.entryName);

        if (entry.isDirectory) {
            fs.mkdirSync(destPath, { recursive: true });
        } else {
            fs.mkdirSync(path.dirname(destPath), { recursive: true });
            fs.writeFileSync(destPath, entry.getData());
        }
    });
}

export { extractZip };
