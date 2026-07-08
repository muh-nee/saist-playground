import java.security.KeyPair
import java.security.KeyPairGenerator

fun rsaKeyPair(): KeyPair {
    val gen = KeyPairGenerator.getInstance("RSA")
    gen.initialize(1024)
    return gen.generateKeyPair()
}
