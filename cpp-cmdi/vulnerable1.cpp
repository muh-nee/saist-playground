#include <cstdlib>
#include <string>

void listDirectory(const std::string& directory) {
    std::system(("ls -la " + directory).c_str());
}
