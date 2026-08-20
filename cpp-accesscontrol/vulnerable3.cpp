#include <string>

struct Request { std::string query(const std::string&) const; };
std::string accountBalance(const std::string& accountId);

std::string balance(const Request& request) {
    return accountBalance(request.query("account_id"));
}
