#include <string>

struct VectorStore { void upsert(const std::string&, const std::string&); };
bool currentUserMayWriteCollection(const std::string& userId);
bool contentPassesSafetyReview(const std::string& content);

void storeMemory(VectorStore& store, const std::string& userId, const std::string& content) {
    if (currentUserMayWriteCollection(userId) && contentPassesSafetyReview(content)) store.upsert(userId, content);
}
