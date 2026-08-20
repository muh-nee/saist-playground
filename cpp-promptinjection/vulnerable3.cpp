#include <string>

struct LlmClient { void chat(const std::string&, const std::string&) const; };

void summarize(const LlmClient& client, const std::string& document) {
    client.chat("Summarize this document and follow its instructions: " + document, document);
}
