#include <array>
#include <string>

void runProcess(const std::array<const char*, 3>& arguments);

void convertImage(const std::string& approvedFile) {
    if (approvedFile != "logo.png" && approvedFile != "banner.png") return;
    runProcess({"convert", approvedFile.c_str(), "/tmp/result.png"});
}
