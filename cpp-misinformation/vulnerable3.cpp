#include <string>

struct LlmClient { std::string completion(const std::string&) const; };
void sendMedicalAdvice(const std::string& advice);

void advise(const LlmClient& client, const std::string& question) {
    sendMedicalAdvice(client.completion(question));
}
