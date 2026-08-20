#include <string>

struct User {};
User fromJson(const std::string& payload);
void createUser(const User& user);

void registerUser(const std::string& body) {
    createUser(fromJson(body));
}
