#include <string>

struct Response { void send(const std::string& body); };
struct LlmClient { std::string completion(const std::string& prompt) const; };

void answer(const LlmClient& client, Response& response, const std::string& question) {
    response.send(client.completion(question));
}
