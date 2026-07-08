import javax.crypto.Cipher
import javax.crypto.KeyGenerator

fun tripleDes(): Cipher {
    val key = KeyGenerator.getInstance("DESede").generateKey()
    val cipher = Cipher.getInstance("DESede/CBC/PKCS5Padding")
    cipher.init(Cipher.ENCRYPT_MODE, key)
    return cipher
}
