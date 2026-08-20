#include <cstdio>
#include <string>

void inspectLog(const std::string& requestedFile) {
    FILE* output = popen(("cat " + requestedFile).c_str(), "r");
    pclose(output);
}
