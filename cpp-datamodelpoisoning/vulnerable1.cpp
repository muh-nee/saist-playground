#include <string>

void importTrainingData(const std::string& path);
void trainModel();

void updateModel(const std::string& requestedDataset) {
    importTrainingData(requestedDataset);
    trainModel();
}
