import Logging
import Vapor

func auditLogin(_ request: Request, logger: Logger) throws {
    let username = try request.query.get(String.self, at: "username")
    logger.info("Login attempt for \(username)")
}
