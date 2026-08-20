#include <string>

void loadPlugin(const std::string& pluginPath);

void enablePlugin(const std::string& requestedPlugin) {
    loadPlugin(requestedPlugin);
}
