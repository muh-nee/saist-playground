import jakarta.servlet.http.HttpServletRequest
import org.slf4j.LoggerFactory

val logger = LoggerFactory.getLogger("RequestLogger")

fun handleRequest(request: HttpServletRequest) {
    val ip = request.getHeader("X-Forwarded-For")
    val sanitized = ip?.replace("\r", "")?.replace("\n", "") ?: ""
    logger.warn("request_received ip={}", sanitized) // CRLF stripped; value in structured field
}
