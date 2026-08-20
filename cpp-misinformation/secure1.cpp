#include <string>

struct Response { void send(const std::string& body); };
struct LlmClient { std::string completion(const std::string& prompt) const; };
bool hasCitations(const std::string& answer);

void answer(const LlmClient& client, Response& response, const std::string& question) {
    const auto generated = client.completion(question);
    if (!hasCitations(generated)) return;
    response.send("AI-generated content; verify cited sources.\n" + generated);
}
