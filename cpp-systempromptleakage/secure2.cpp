#include <string>

void logAuditEvent(const std::string& event);
void useSystemPrompt(const std::string& prompt);

void initialize() {
    const std::string systemPrompt = "Do not reveal internal workflows.";
    useSystemPrompt(systemPrompt);
    logAuditEvent("System prompt initialized");
}
