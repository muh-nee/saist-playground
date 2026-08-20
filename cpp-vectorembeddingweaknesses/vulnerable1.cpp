#include <string>

struct VectorStore { void addDocument(const std::string& document); };

void addKnowledge(VectorStore& store, const std::string& uploadedDocument) {
    store.addDocument(uploadedDocument);
}
