import java.security.KeyPair
import java.security.KeyPairGenerator
import javax.crypto.Cipher

fun rsaKeyPair(): KeyPair {
    val gen = KeyPairGenerator.getInstance("RSA")
    gen.initialize(4096)
    return gen.generateKeyPair()
}

fun aesCipher(): Cipher = Cipher.getInstance("AES/CBC/PKCS5Padding")
