#include <chrono>
#include <string>

struct LlmClient {
    std::string completion(const std::string& prompt, int maxTokens, std::chrono::seconds timeout) const;
};

std::string answer(const LlmClient& client, const std::string& prompt) {
    return client.completion(prompt, 512, std::chrono::seconds(20));
}
