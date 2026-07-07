import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api")
class UserController(private val userService: UserService) {

    @GetMapping("/user/{id}")
    fun getUser(@PathVariable id: Long): ResponseEntity<Any> {
        return try {
            ResponseEntity.ok(userService.getUser(id))
        } catch (e: Exception) {
            ResponseEntity.status(500).body(mapOf("error" to e.message))
        }
    }
}

interface UserService {
    fun getUser(id: Long): Any
}
