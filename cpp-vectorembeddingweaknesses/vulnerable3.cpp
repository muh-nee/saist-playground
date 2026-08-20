#include <string>

struct VectorStore { void addDocument(const std::string&); };

void importWebPage(VectorStore& store, const std::string& downloadedPage) {
    store.addDocument(downloadedPage);
}
