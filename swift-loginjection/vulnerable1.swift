import Logging

func auditLogin(username: String, logger: Logger) {
    logger.info("Login attempt for \(username)") // VULNERABLE: newlines can forge log entries
}
