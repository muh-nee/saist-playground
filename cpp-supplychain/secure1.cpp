#include <string>

bool matchesPinnedChecksum(const std::string& packagePath, const std::string& checksum);
void installSignedPlugin(const std::string& packagePath);

void installPlugin(const std::string& packagePath, const std::string& expectedChecksum) {
    if (!matchesPinnedChecksum(packagePath, expectedChecksum)) return;
    installSignedPlugin(packagePath);
}
