import java.io.File
import java.nio.file.Paths

fun readReport(reportId: String): ByteArray {
    val safeName = Paths.get(reportId).fileName.toString()
    return File("/data/reports", safeName).readBytes()
}
