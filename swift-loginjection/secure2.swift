import Logging

func auditLogin(userID: UUID, logger: Logger) {
    logger.info("Login attempt", metadata: ["user_id": .string(userID.uuidString)])
}
