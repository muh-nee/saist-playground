import SQLite3

func deleteOrder(id: String, db: OpaquePointer) {
    sqlite3_exec(db, "DELETE FROM orders WHERE id = \(id)", nil, nil, nil)
}
