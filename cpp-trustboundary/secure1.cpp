#include <string>

struct Request { std::string sessionId() const; };
std::string roleForSession(const std::string& sessionId);
bool allowsAdminAction(const std::string& role);
void deleteAllRecords();

void handleRequest(const Request& request) {
    if (allowsAdminAction(roleForSession(request.sessionId()))) deleteAllRecords();
}
