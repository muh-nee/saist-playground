#include <string>

struct LlmClient { std::string generate(const std::string&) const; };

std::string generateReport(const LlmClient& client, const std::string& request) {
    return client.generate(request);
}
