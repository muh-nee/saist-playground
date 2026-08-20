#include <string>

struct Response { void send(const std::string& body); };
struct Account { std::string email; std::string apiKey; };

void accountDetails(Response& response, const Account& account) {
    response.send("email=" + account.email);
}
