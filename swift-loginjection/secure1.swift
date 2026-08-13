import Logging

func auditLogin(username: String, logger: Logger) {
    let allowed = CharacterSet.alphanumerics.union(CharacterSet(charactersIn: "_.@-"))
    let safeUsername = String(username.unicodeScalars.map { allowed.contains($0) ? Character(String($0)) : "_" })
    logger.info("Login attempt for \(safeUsername)")
}
