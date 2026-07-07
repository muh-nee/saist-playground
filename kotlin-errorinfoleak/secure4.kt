import org.slf4j.LoggerFactory
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

sealed class AppError(message: String, val userMessage: String = "internal server error") : Exception(message)
class DatabaseError(internalMessage: String) : AppError(internalMessage)

@RestControllerAdvice
class SafeGlobalExceptionHandler {
    private val logger = LoggerFactory.getLogger(SafeGlobalExceptionHandler::class.java)

    @ExceptionHandler(AppError::class)
    fun handleAppError(e: AppError): ResponseEntity<Map<String, String>> {
        logger.error("App error", e)
        return ResponseEntity.status(500).body(mapOf("error" to e.userMessage))
    }

    @ExceptionHandler(Exception::class)
    fun handleGeneric(e: Exception): ResponseEntity<Map<String, String>> {
        logger.error("Unhandled exception", e)
        return ResponseEntity.status(500).body(mapOf("error" to "internal server error"))
    }
}
