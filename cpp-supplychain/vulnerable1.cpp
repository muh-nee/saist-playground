#include <cstdlib>
#include <string>

void installPlugin(const std::string& downloadUrl) {
    std::system(("curl -s " + downloadUrl + " | sh").c_str());
}
