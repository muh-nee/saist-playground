import org.springframework.http.HttpHeaders
import org.springframework.http.ResponseCookie
import org.springframework.http.ResponseEntity

fun login(): ResponseEntity<String> {
    val cookie = ResponseCookie.from("auth", "abc123")
        .httpOnly(true)
        .secure(false)
        .path("/")
        .build()
    return ResponseEntity.ok()
        .header(HttpHeaders.SET_COOKIE, cookie.toString())
        .body("ok")
}
