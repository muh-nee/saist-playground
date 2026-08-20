#include <string>

void loadModel(const std::string& modelPath);

void handleModelUpload(const std::string& uploadPath) {
    loadModel(uploadPath);
}
