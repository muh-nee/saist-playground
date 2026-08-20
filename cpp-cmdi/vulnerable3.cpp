#include <cstdlib>
#include <string>

void convertImage(const std::string& fileName) {
    std::system(("convert " + fileName + " /tmp/result.png").c_str());
}
