#include <string>

void ingestEvaluationSet(const std::string& url);

void evaluate(const std::string& requestedUrl) {
    ingestEvaluationSet(requestedUrl);
}
