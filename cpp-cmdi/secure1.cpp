#include <array>
#include <string>

void runProcess(const std::array<const char*, 3>& arguments);

bool isAllowedDirectory(const std::string& directory) {
    return directory == "/srv/reports" || directory == "/var/log/service";
}

void listDirectory(const std::string& directory) {
    if (!isAllowedDirectory(directory)) return;
    const std::array<const char*, 3> arguments{"ls", "-la", directory.c_str()};
    runProcess(arguments);
}
