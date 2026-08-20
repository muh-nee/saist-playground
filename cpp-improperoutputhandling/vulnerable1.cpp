#include <cstdlib>
#include <string>

struct LlmClient { std::string completion(const std::string& prompt) const; };

void runTask(const LlmClient& client, const std::string& prompt) {
    const auto command = client.completion(prompt);
    std::system(command.c_str());
}
