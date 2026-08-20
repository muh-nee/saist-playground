#include <string>

struct Settings {};
Settings deserializeSettings(const std::string& payload);
void apply(const Settings& settings);

void loadSettings(const std::string& requestBody) {
    apply(deserializeSettings(requestBody));
}
