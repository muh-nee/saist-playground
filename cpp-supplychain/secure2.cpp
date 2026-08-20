#include <string>

bool isApprovedPlugin(const std::string& pluginPath);
void loadPlugin(const std::string& pluginPath);

void enablePlugin(const std::string& requestedPlugin) {
    if (isApprovedPlugin(requestedPlugin)) loadPlugin(requestedPlugin);
}
