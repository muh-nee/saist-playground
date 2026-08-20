#include <string>

struct Agent { std::string suggestedAction(const std::string&) const; };
void changeAccountPermissions(const std::string& action);

void process(const Agent& agent, const std::string& request) {
    changeAccountPermissions(agent.suggestedAction(request));
}
