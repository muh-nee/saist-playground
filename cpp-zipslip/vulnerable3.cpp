#include <filesystem>
#include <fstream>
#include <string>

struct ArchiveEntry { std::string name() const; };

void extract(const ArchiveEntry& entry) {
    const std::filesystem::path outputPath = "/srv/extract/" + entry.name();
    std::ofstream(outputPath) << "content";
}
