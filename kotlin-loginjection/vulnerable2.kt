import jakarta.servlet.http.HttpServletRequest
import org.slf4j.LoggerFactory

val logger = LoggerFactory.getLogger("RequestLogger")

fun handleRequest(request: HttpServletRequest) {
    val ip = request.getHeader("X-Forwarded-For")
    logger.warn("Request received from: " + ip) // concatenation injects ip into message
}
