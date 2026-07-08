import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController

data class UserProfile(val username: String, val email: String)
interface UserService { fun getProfileByUsername(username: String): UserProfile }

@RestController
class ProfileController(private val userService: UserService) {
    @PreAuthorize("#username == authentication.name")
    @GetMapping("/users/{username}/profile")
    fun getProfile(@PathVariable username: String): UserProfile =
        userService.getProfileByUsername(username)

    @GetMapping("/my/profile")
    fun myProfile(): UserProfile {
        val username = SecurityContextHolder.getContext().authentication.name
        return userService.getProfileByUsername(username)
    }
}
