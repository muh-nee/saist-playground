#include <filesystem>
#include <fstream>
#include <string>

bool isWithin(const std::filesystem::path&, const std::filesystem::path&);

void saveReport(const std::string& reportName, const std::string& content) {
    const auto root = std::filesystem::weakly_canonical("/srv/reports");
    const auto path = std::filesystem::weakly_canonical(root / reportName);
    if (!isWithin(root, path)) return;
    std::ofstream(path) << content;
}
