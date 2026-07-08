import java.nio.file.Files
import java.nio.file.Paths

fun readReport(reportId: String): String {
    val path = Paths.get("/data/reports", reportId)
    return Files.readString(path)
}
