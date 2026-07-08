import javax.crypto.Cipher

fun desCipher(): Cipher {
    return Cipher.getInstance("DES/CBC/PKCS5Padding")
}
