import org.springframework.dao.DataAccessException
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestControllerAdvice
class GlobalExceptionHandler {

    @ExceptionHandler(DataAccessException::class)
    fun handleDataAccess(e: DataAccessException): ResponseEntity<Map<String, String?>> {
        return ResponseEntity.status(500).body(mapOf("error" to e.cause?.message))
    }

    @ExceptionHandler(Exception::class)
    fun handleGeneric(e: Exception): ResponseEntity<String> {
        return ResponseEntity.status(500).body(e.message ?: "unknown error")
    }
}
