#include <sqlite3.h>
#include <string>

void findUser(sqlite3* database, const std::string& username) {
    sqlite3_stmt* statement = nullptr;
    sqlite3_prepare_v2(database, "SELECT * FROM users WHERE name = ?", -1, &statement, nullptr);
    sqlite3_bind_text(statement, 1, username.c_str(), -1, SQLITE_TRANSIENT);
    sqlite3_step(statement);
    sqlite3_finalize(statement);
}
