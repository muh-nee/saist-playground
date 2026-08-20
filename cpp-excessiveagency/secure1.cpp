#include <string>

struct Agent { std::string nextTool(const std::string& prompt) const; };
bool isPermittedTool(const std::string& tool);
void executeReadOnlyTool(const std::string& tool);

void handleAgentRequest(const Agent& agent, const std::string& prompt) {
    const auto tool = agent.nextTool(prompt);
    if (!isPermittedTool(tool)) return;
    executeReadOnlyTool(tool);
}
