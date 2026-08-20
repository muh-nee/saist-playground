#include <string>

void log(const std::string& message);

void authenticate(const std::string& username, const std::string& password) {
    log("login for " + username + " password=" + password);
}
