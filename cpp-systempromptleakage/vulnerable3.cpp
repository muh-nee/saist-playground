#include <string>

struct Response { void send(const std::string&); };

void exportConversation(Response& response, const std::string& systemPrompt, const std::string& answer) {
    response.send("system=" + systemPrompt + "\nanswer=" + answer);
}
