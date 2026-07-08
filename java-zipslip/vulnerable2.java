import java.io.*;
import java.nio.file.*;
import java.util.zip.*;

/**
 * VULNERABLE: Extracts a ZipFile without validating entry paths.
 * Paths.get(destDir).resolve(entry.getName()) allows "../" traversal.
 */
public class ZipExtractVulnerable2 {

    public static void extractZipFile(ZipFile zipFile, Path destDir) throws IOException {
        var entries = zipFile.entries();
        while (entries.hasMoreElements()) {
            ZipEntry entry = entries.nextElement();

            // VULNERABLE: resolve() does not prevent "../" traversal
            Path destPath = destDir.resolve(entry.getName());

            if (entry.isDirectory()) {
                Files.createDirectories(destPath);
            } else {
                Files.createDirectories(destPath.getParent());
                try (InputStream in = zipFile.getInputStream(entry)) {
                    Files.copy(in, destPath, StandardCopyOption.REPLACE_EXISTING);
                }
            }
        }
    }
}
