import java.io.File
import java.io.FileOutputStream
import java.io.InputStream
import java.util.zip.ZipInputStream

/**
 * VULNERABLE: Extracts a zip stream without validating entry paths.
 * entry.name may contain "../" sequences that escape destDir.
 */
fun extractZip(inputStream: InputStream, destDir: File) {
    ZipInputStream(inputStream).use { zis ->
        var entry = zis.nextEntry
        while (entry != null) {
            // VULNERABLE: File(destDir, entry.name) does not sanitize "../"
            val destFile = File(destDir, entry.name)

            if (entry.isDirectory) {
                destFile.mkdirs()
            } else {
                destFile.parentFile?.mkdirs()
                destFile.outputStream().use { out ->
                    zis.copyTo(out)
                }
            }
            entry = zis.nextEntry
        }
    }
}
