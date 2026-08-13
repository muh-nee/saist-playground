import GRDB

func user(id: String, db: Database) throws -> Row? {
    try Row.fetchOne(db, sql: "SELECT * FROM users WHERE id = '\(id)'") // VULNERABLE: SQL interpolation
}
