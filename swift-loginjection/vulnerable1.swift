import Logging

func auditLogin(username: String, logger: Logger) {
    logger.info("Login attempt for \(username)")
}
