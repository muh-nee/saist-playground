import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder

fun hashPassword(password: String): String {
    val encoder = BCryptPasswordEncoder()
    return encoder.encode(password)
}
