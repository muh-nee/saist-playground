import org.springframework.http.HttpHeaders
import org.springframework.http.ResponseCookie
import org.springframework.http.ResponseEntity

fun login(sessionId: String): ResponseEntity<String> {
    val cookie = ResponseCookie.from("SESSIONID", sessionId)
        .httpOnly(true)
        .secure(true)
        .sameSite("Strict")
        .path("/")
        .build()
    return ResponseEntity.ok()
        .header(HttpHeaders.SET_COOKIE, cookie.toString())
        .body("ok")
}
