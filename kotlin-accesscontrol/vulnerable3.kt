import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController

data class UserProfile(val userId: Long, val email: String)
interface UserService { fun getProfile(userId: Long): UserProfile }

@RestController
class ProfileController(private val userService: UserService) {
    @GetMapping("/users/{userId}/profile")
    fun getProfile(@PathVariable userId: Long): UserProfile =
        userService.getProfile(userId)
}
