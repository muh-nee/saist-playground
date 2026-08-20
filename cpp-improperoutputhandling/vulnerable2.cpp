#include <string>

struct LlmClient { std::string completion(const std::string&) const; };
void executeQuery(const std::string& query);

void report(const LlmClient& client, const std::string& request) {
    executeQuery(client.completion(request));
}
