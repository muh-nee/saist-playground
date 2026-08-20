#include <cstdlib>
#include <string>

void installDependency(const std::string& packageName) {
    std::system(("vcpkg install " + packageName).c_str());
}
