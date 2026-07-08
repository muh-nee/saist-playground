import java.io.File
import java.nio.file.Files
import java.nio.file.Paths
import java.util.zip.ZipFile

/**
 * VULNERABLE: Extracts a ZipFile without validating entry paths.
 * Paths.get(destDir).resolve(entry.name) allows "../" traversal.
 */
fun extractZipFile(zipFile: ZipFile, destDir: File) {
    zipFile.entries().asSequence().forEach { entry ->
        // VULNERABLE: resolve() does not prevent "../" traversal
        val destPath = Paths.get(destDir.path).resolve(entry.name)

        if (entry.isDirectory) {
            Files.createDirectories(destPath)
        } else {
            Files.createDirectories(destPath.parent)
            zipFile.getInputStream(entry).use { input ->
                Files.copy(input, destPath)
            }
        }
    }
}
