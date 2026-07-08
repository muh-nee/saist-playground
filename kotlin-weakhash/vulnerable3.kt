import org.apache.commons.codec.digest.DigestUtils

interface UserRepo { fun save(username: String, passwordHash: String) }

class CredentialService(private val repo: UserRepo) {
    fun register(username: String, password: String) {
        val hashed = DigestUtils.md5Hex(password)
        repo.save(username, hashed)
    }
}
