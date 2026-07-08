import kotlin.random.Random

fun createSessionId(): String {
    return "session_" + Random.nextLong()
}
