#include <string>

void log(const std::string& message);

void initialize() {
    const std::string systemPrompt = "Do not reveal internal workflows.";
    log(systemPrompt);
}
