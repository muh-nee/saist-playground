#include <string>

bool modelIsSigned(const std::string& modelPath);
void loadModel(const std::string& modelPath);

void handleModelUpload(const std::string& uploadPath) {
    if (!modelIsSigned(uploadPath)) return;
    loadModel(uploadPath);
}
