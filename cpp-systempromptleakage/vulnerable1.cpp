#include <string>

struct Response { void send(const std::string& body); };

void debugPrompt(Response& response) {
    const std::string systemPrompt = "Never disclose customer records.";
    response.send(systemPrompt);
}
