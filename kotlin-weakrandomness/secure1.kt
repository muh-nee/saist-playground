import java.security.SecureRandom
import java.util.Base64

fun generateToken(): String {
    val random = SecureRandom()
    val bytes = ByteArray(32)
    random.nextBytes(bytes)
    return Base64.getEncoder().encodeToString(bytes)
}
