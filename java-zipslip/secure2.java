import java.io.*;
import java.nio.file.*;
import java.util.zip.*;

/**
 * Safe: Extracts a ZipFile using Path.normalize().startsWith() check.
 * Validates that resolved path stays within destination directory.
 */
public class ZipExtractSafe2 {

    public static void extractZipFile(ZipFile zipFile, Path destDir) throws IOException {
        Path normalizedDest = destDir.toAbsolutePath().normalize();

        var entries = zipFile.entries();
        while (entries.hasMoreElements()) {
            ZipEntry entry = entries.nextElement();

            // Safe: normalize() resolves ".." and we verify containment
            Path entryPath = normalizedDest.resolve(entry.getName()).normalize();
            if (!entryPath.startsWith(normalizedDest)) {
                throw new IOException("Illegal entry: " + entry.getName());
            }

            if (entry.isDirectory()) {
                Files.createDirectories(entryPath);
            } else {
                Files.createDirectories(entryPath.getParent());
                try (InputStream in = zipFile.getInputStream(entry)) {
                    Files.copy(in, entryPath, StandardCopyOption.REPLACE_EXISTING);
                }
            }
        }
    }
}
