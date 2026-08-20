#include <string>

struct Agent { std::string nextTool(const std::string& prompt) const; };
void executePrivilegedTool(const std::string& tool);

void handleAgentRequest(const Agent& agent, const std::string& prompt) {
    executePrivilegedTool(agent.nextTool(prompt));
}
