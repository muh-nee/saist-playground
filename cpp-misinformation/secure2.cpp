#include <string>

struct LlmClient { std::string completion(const std::string&) const; };
bool isReviewedByEditor(const std::string& article);
void publishArticle(const std::string& article);

void publish(const LlmClient& client, const std::string& topic) {
    const auto article = client.completion(topic);
    if (isReviewedByEditor(article)) publishArticle(article);
}
