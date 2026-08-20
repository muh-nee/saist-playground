#include <string>

struct LlmClient { void setSystemPrompt(const std::string&) const; };

void configure(const LlmClient& client, const std::string& customerInstructions) {
    client.setSystemPrompt("Follow company policy. " + customerInstructions);
}
