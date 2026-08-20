#include <string>

struct LlmClient { std::string completion(const std::string&) const; };
void writeConfiguration(const std::string& config);

void configure(const LlmClient& client, const std::string& request) {
    writeConfiguration(client.completion(request));
}
