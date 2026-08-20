#include <string>

bool hasTrustedSignature(const std::string& path);
void importTrainingData(const std::string& path);
void trainModel();

void updateModel(const std::string& approvedDataset) {
    if (!hasTrustedSignature(approvedDataset)) return;
    importTrainingData(approvedDataset);
    trainModel();
}
