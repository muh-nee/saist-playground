#include <string>

struct VectorStore { void upsert(const std::string&, const std::string&); };

void storeMemory(VectorStore& store, const std::string& userId, const std::string& content) {
    store.upsert(userId, content);
}
