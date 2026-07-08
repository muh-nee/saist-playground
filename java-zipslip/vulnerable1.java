import java.io.*;
import java.util.zip.*;

/**
 * VULNERABLE: Extracts a zip file without validating entry paths.
 * A malicious archive entry with "../" in its name will escape destDir.
 */
public class ZipExtractVulnerable1 {

    public static void extractZip(InputStream inputStream, File destDir) throws IOException {
        try (ZipInputStream zis = new ZipInputStream(inputStream)) {
            ZipEntry entry;
            while ((entry = zis.getNextEntry()) != null) {
                // VULNERABLE: new File(destDir, entry.getName()) does not sanitize "../"
                File destFile = new File(destDir, entry.getName());

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
