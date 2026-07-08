import java.util.Random

fun generatePassword(length: Int): String {
    val random = Random()
    val chars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789"
    return (1..length).map { chars[random.nextInt(chars.length)] }.joinToString("")
}
