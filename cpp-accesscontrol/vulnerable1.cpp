#include <string>

struct Request { std::string query(const std::string& key) const; };
struct DocumentStore { std::string find(const std::string& id) const; };

std::string getDocument(const Request& request, const DocumentStore& documents) {
    return documents.find(request.query("document_id"));
}
