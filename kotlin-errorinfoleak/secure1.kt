import org.slf4j.LoggerFactory
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api")
class SafeUserController(private val userService: UserService) {
    private val logger = LoggerFactory.getLogger(SafeUserController::class.java)

    @GetMapping("/user/{id}")
    fun getUser(@PathVariable id: Long): ResponseEntity<Any> {
        return try {
            ResponseEntity.ok(userService.getUser(id))
        } catch (e: Exception) {
            logger.error("getUser failed for id {}", id, e)
            ResponseEntity.status(500).body(mapOf("error" to "internal server error"))
        }
    }
}
