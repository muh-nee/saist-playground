import java.io.*;
import java.util.zip.*;

/**
 * Safe: Extracts a zip file with canonical path validation.
 * Uses getCanonicalPath() to detect and reject path traversal.
 */
public class ZipExtractSafe1 {

    public static void extractZip(InputStream inputStream, File destDir) throws IOException {
        String canonicalDest = destDir.getCanonicalPath();

        try (ZipInputStream zis = new ZipInputStream(inputStream)) {
            ZipEntry entry;
            while ((entry = zis.getNextEntry()) != null) {
                File destFile = new File(destDir, entry.getName());

                // Safe: getCanonicalPath() resolves all ".." and symlinks
                if (!destFile.getCanonicalPath().startsWith(canonicalDest + File.separator)) {
                    throw new IOException("Illegal entry path: " + entry.getName());
                }

                if (entry.isDirectory()) {
                    destFile.mkdirs();
                } else {
                    destFile.getParentFile().mkdirs();
                    try (FileOutputStream fos = new FileOutputStream(destFile)) {
                        byte[] buffer = new byte[4096];
                        int len;
                        while ((len = zis.read(buffer)) > 0) {
                            fos.write(buffer, 0, len);
                        }
                    }
                }
                zis.closeEntry();
            }
        }
    }
}
