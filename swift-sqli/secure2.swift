import SQLite3

func deleteOrder(id: Int64, db: OpaquePointer) {
    var statement: OpaquePointer?
    sqlite3_prepare_v2(db, "DELETE FROM orders WHERE id = ?", -1, &statement, nil)
    sqlite3_bind_int64(statement, 1, id)
    sqlite3_step(statement)
}
