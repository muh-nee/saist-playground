#include <string>

struct Agent { std::string toolCall(const std::string&) const; };
void deployProduction(const std::string& command);

void process(const Agent& agent, const std::string& request) {
    deployProduction(agent.toolCall(request));
}
