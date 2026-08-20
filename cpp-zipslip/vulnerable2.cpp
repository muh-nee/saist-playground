#include <fstream>
#include <string>

struct ArchiveEntry { std::string pathname() const; };

void extract(const ArchiveEntry& entry) {
    std::ofstream output("/var/tmp/archive/" + entry.pathname());
    output << "content";
}
