#include <string>

struct Database { void query(const std::string&, const std::string&); };

void search(Database& database, const std::string& term) {
    database.query("SELECT * FROM products WHERE name LIKE ?", term);
}
