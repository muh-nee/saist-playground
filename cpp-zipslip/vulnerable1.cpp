#include <fstream>
#include <string>

struct ArchiveEntry { std::string name() const; std::string contents() const; };

void extract(const ArchiveEntry& entry) {
    std::ofstream output("/srv/extract/" + entry.name());
    output << entry.contents();
}
