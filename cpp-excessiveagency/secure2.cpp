#include <string>

struct Agent { std::string suggestedAction(const std::string&) const; };
bool userMayModifyProfile(const std::string& userId);
void updateOwnProfile(const std::string& userId);

void process(const Agent& agent, const std::string& request, const std::string& userId) {
    if (agent.suggestedAction(request) == "update_profile" && userMayModifyProfile(userId)) updateOwnProfile(userId);
}
