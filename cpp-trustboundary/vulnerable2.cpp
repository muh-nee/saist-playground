#include <string>

struct Request { std::string header(const std::string&) const; };
void approvePayment();

void pay(const Request& request) {
    if (request.header("X-Approved") == "true") approvePayment();
}
