#include <filesystem>
#include <fstream>
#include <string>

struct ArchiveEntry { std::string name() const; std::string contents() const; };
bool isWithin(const std::filesystem::path& root, const std::filesystem::path& path);

void extract(const ArchiveEntry& entry) {
    const auto root = std::filesystem::weakly_canonical("/srv/extract");
    const auto outputPath = std::filesystem::weakly_canonical(root / entry.name());
    if (!isWithin(root, outputPath)) return;
    std::ofstream output(outputPath);
    output << entry.contents();
}
