#include <string>

struct Request { std::string bodyField(const std::string&) const; };
void grantAdministrator(const std::string& userId);

void updateRole(const Request& request) {
    grantAdministrator(request.bodyField("user_id"));
}
