import java.io.File
import java.nio.file.Files
import java.util.zip.ZipFile

/**
 * Safe: Extracts a ZipFile using canonicalFile for path validation.
 * Verifies each entry resolves within destDir before writing.
 */
fun extractZipFileSafe(zipFile: ZipFile, destDir: File) {
    val canonicalDest = destDir.canonicalPath

    zipFile.entries().asSequence().forEach { entry ->
        // Safe: canonicalFile resolves all ".." and we verify containment
        val destFile = File(destDir, entry.name).canonicalFile

        if (!destFile.path.startsWith(canonicalDest + File.separator)) {
            throw SecurityException("Illegal entry: ${entry.name}")
        }

        if (entry.isDirectory) {
            destFile.mkdirs()
        } else {
            destFile.parentFile?.mkdirs()
            zipFile.getInputStream(entry).use { input ->
                Files.copy(input, destFile.toPath())
            }
        }
    }
}
