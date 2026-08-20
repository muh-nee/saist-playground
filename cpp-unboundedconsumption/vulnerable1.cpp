#include <string>

struct LlmClient { std::string completion(const std::string& prompt) const; };

std::string answer(const LlmClient& client, const std::string& prompt) {
    return client.completion(prompt);
}
