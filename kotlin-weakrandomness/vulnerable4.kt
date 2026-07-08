import java.util.Random

fun verificationCode(): Int {
    val random = Random(System.currentTimeMillis())
    return random.nextInt(1_000_000)
}
