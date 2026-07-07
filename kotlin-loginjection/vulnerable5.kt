import org.springframework.web.bind.annotation.*
import org.slf4j.LoggerFactory

@RestController
class AuditController {
    private val logger = LoggerFactory.getLogger(AuditController::class.java)

    @PostMapping("/audit")
    fun audit(@RequestParam action: String) {
        logger.error("User performed: $action") // string template injects action into message
    }
}
