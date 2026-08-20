#include <sqlite3.h>
#include <string>

void findUser(sqlite3* database, const std::string& username) {
    const auto query = "SELECT * FROM users WHERE name = '" + username + "'";
    sqlite3_exec(database, query.c_str(), nullptr, nullptr, nullptr);
}
