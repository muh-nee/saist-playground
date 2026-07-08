import com.google.common.hash.Hashing
import java.nio.charset.StandardCharsets

fun passwordHash(password: String): String =
    Hashing.md5().hashString(password, StandardCharsets.UTF_8).toString()
