#include <filesystem>
#include <fstream>
#include <string>

struct ArchiveEntry { std::string name() const; };
bool isWithin(const std::filesystem::path&, const std::filesystem::path&);

void extract(const ArchiveEntry& entry) {
    const auto root = std::filesystem::weakly_canonical("/srv/extract");
    const auto path = std::filesystem::weakly_canonical(root / entry.name());
    if (isWithin(root, path)) std::ofstream(path) << "content";
}
