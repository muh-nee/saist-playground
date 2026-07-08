import java.io.File
import java.io.InputStream
import java.util.zip.ZipInputStream

/**
 * Safe: Extracts a zip stream with canonicalPath validation.
 * Verifies each entry resolves within destDir before writing.
 */
fun extractZipSafe(inputStream: InputStream, destDir: File) {
    val canonicalDest = destDir.canonicalPath

    ZipInputStream(inputStream).use { zis ->
        var entry = zis.nextEntry
        while (entry != null) {
            val destFile = File(destDir, entry.name)

            // Safe: canonicalPath resolves all ".." and symlinks
            if (!destFile.canonicalPath.startsWith(canonicalDest + File.separator)) {
                throw SecurityException("Illegal entry: ${entry.name}")
            }

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
