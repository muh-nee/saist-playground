#include <fstream>
#include <string>

std::string readFile(const std::string& requestedFile) {
    std::ifstream file("/srv/uploads/" + requestedFile);
    return {std::istreambuf_iterator<char>(file), std::istreambuf_iterator<char>()};
}
