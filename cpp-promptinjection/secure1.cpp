#include <string>

struct LlmClient { void chat(const std::string& systemPrompt, const std::string& userMessage) const; };

void answer(const LlmClient& client, const std::string& message) {
    client.chat("You are a helpful support assistant.", message);
}
