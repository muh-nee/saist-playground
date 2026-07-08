import java.io.File

fun download(name: String): ByteArray {
    val base = File("/var/www/files").canonicalFile
    val target = File(base, name).canonicalFile
    require(target.toPath().startsWith(base.toPath())) { "path traversal detected" }
    return target.readBytes()
}
