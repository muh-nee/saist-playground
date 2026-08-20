#include <string>

struct Response { void send(const std::string& body); };
void logPromptForAuthorizedDiagnostics(const std::string& prompt);

void debugPrompt(Response& response) {
    const std::string systemPrompt = "Never disclose customer records.";
    logPromptForAuthorizedDiagnostics(systemPrompt);
    response.send("Prompt configuration loaded.");
}
