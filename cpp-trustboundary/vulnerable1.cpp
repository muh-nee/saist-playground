#include <string>

struct Request { std::string cookie(const std::string& name) const; };
bool allowsAdminAction(const std::string& role);
void deleteAllRecords();

void handleRequest(const Request& request) {
    if (allowsAdminAction(request.cookie("role"))) deleteAllRecords();
}
