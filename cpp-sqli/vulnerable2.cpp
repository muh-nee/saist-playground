#include <string>

void mysql_query(void* database, const char* query);

void deleteUser(void* database, const std::string& userId) {
    const auto query = "DELETE FROM users WHERE id = " + userId;
    mysql_query(database, query.c_str());
}
