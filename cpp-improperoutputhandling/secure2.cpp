#include <string>

struct LlmClient { std::string completion(const std::string&) const; };
bool matchesReportSchema(const std::string& report);
void storeReport(const std::string& report);

void report(const LlmClient& client, const std::string& request) {
    const auto generated = client.completion(request);
    if (matchesReportSchema(generated)) storeReport(generated);
}
