#include <string>

struct LlmClient { std::string completion(const std::string& prompt) const; };
bool isAllowedAction(const std::string& action);
void executeAction(const std::string& action);

void runTask(const LlmClient& client, const std::string& prompt) {
    const auto action = client.completion(prompt);
    if (!isAllowedAction(action)) return;
    executeAction(action);
}
