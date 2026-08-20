#include <string>

void PQexec(void* connection, const char* query);

void search(void* connection, const std::string& term) {
    const auto query = "SELECT * FROM products WHERE name LIKE '%" + term + "%'";
    PQexec(connection, query.c_str());
}
