#include <filesystem>
#include <fstream>
#include <string>

bool isWithin(const std::filesystem::path& root, const std::filesystem::path& path);

std::string readFile(const std::string& requestedFile) {
    const auto root = std::filesystem::weakly_canonical("/srv/uploads");
    const auto path = std::filesystem::weakly_canonical(root / requestedFile);
    if (!isWithin(root, path)) return {};
    std::ifstream file(path);
    return {std::istreambuf_iterator<char>(file), std::istreambuf_iterator<char>()};
}
