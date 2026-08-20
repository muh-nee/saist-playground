#include <string>

struct LlmClient { std::string completion(const std::string&) const; };
void publishArticle(const std::string& article);

void publish(const LlmClient& client, const std::string& topic) {
    publishArticle(client.completion(topic));
}
