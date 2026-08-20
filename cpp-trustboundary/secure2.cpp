#include <string>

struct Request { std::string bodyField(const std::string&) const; };
bool currentUserMayGrantAdmin(const std::string& userId);
void grantAdministrator(const std::string& userId);

void updateRole(const Request& request, const std::string& currentUserId) {
    if (currentUserMayGrantAdmin(currentUserId)) grantAdministrator(request.bodyField("user_id"));
}
