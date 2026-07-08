import java.security.MessageDigest

fun authToken(userId: String, secret: String): ByteArray {
    val digest = MessageDigest.getInstance("SHA-256")
    return digest.digest((userId + secret).toByteArray())
}

fun cacheKey(content: ByteArray): ByteArray =
    MessageDigest.getInstance("MD5").digest(content)
