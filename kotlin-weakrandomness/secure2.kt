import java.security.SecureRandom
import java.util.Random

fun otp(): Int = SecureRandom().nextInt(1_000_000)

fun shuffledBanners(banners: List<String>): List<String> =
    banners.shuffled(Random())
