import java.security.MessageDigest

fun authToken(userId: String, secret: String): ByteArray {
    val digest = MessageDigest.getInstance("SHA-1")
    return digest.digest((userId + secret).toByteArray())
}
