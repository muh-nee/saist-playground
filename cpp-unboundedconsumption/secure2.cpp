#include <chrono>
#include <string>

struct LlmClient { std::string chat(const std::string&, int, std::chrono::milliseconds) const; };

std::string summarize(const LlmClient& client, const std::string& document) {
    return client.chat("Summarize: " + document, 256, std::chrono::seconds(10));
}
