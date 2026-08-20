#include <string>

struct Settings {};
bool matchesSettingsSchema(const std::string& payload);
Settings deserializeSettings(const std::string& payload);
void apply(const Settings& settings);

void loadSettings(const std::string& requestBody) {
    if (!matchesSettingsSchema(requestBody)) return;
    apply(deserializeSettings(requestBody));
}
