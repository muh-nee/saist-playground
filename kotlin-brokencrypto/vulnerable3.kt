import javax.crypto.Cipher

fun streamCipher(): Cipher {
    return Cipher.getInstance("RC4")
}
