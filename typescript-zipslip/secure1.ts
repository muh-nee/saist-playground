import AdmZip from 'adm-zip';
import * as path from 'path';
import * as fs from 'fs';

/**
 * Safe: Validates entry paths with path.resolve() at runtime before writing.
 * TypeScript types alone are not sufficient — runtime check is required.
 */
function extractZipSafe(zipPath: string, destDir: string): void {
    const resolvedDest: string = path.resolve(destDir);

    const zip = new AdmZip(zipPath);
    zip.getEntries().forEach((entry: AdmZip.IZipEntry) => {
        // Safe: path.resolve eliminates ".." and we verify containment at runtime
        const destPath: string = path.resolve(destDir, entry.entryName);

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

export { extractZipSafe };
