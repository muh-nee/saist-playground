#include <string>

struct LlmClient { std::string chat(const std::string&) const; };

std::string summarize(const LlmClient& client, const std::string& document) {
    return client.chat("Summarize: " + document);
}
