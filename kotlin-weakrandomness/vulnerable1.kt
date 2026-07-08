import java.util.Base64
import java.util.Random

fun generateToken(): String {
    val random = Random()
    val bytes = ByteArray(32)
    random.nextBytes(bytes)
    return Base64.getEncoder().encodeToString(bytes)
}
