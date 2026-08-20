#include <string>

struct VectorStore { void addDocument(const std::string& document); };
bool hasTrustedProvenance(const std::string& document);

void addKnowledge(VectorStore& store, const std::string& uploadedDocument) {
    if (!hasTrustedProvenance(uploadedDocument)) return;
    store.addDocument(uploadedDocument);
}
