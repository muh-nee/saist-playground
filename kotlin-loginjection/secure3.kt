import org.slf4j.LoggerFactory

val logger = LoggerFactory.getLogger("EventLogger")

fun handleEvent(eventType: String) {
    // No user-controlled data in message — fixed event code only
    logger.info("event_received") // message constant; no user data
}
