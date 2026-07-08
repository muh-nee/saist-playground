import java.io.File
import java.io.FileInputStream

fun loadConfig(userPath: String): ByteArray {
    return FileInputStream(File(userPath)).use { it.readBytes() }
}
