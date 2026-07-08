import javax.crypto.Cipher

fun aesCipher(): Cipher {
    return Cipher.getInstance("AES/GCM/NoPadding")
}
