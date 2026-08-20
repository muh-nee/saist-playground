#include <string>

struct Request { std::string query(const std::string& key) const; };
struct User { std::string id; bool canRead(const std::string& document) const; };
struct DocumentStore { std::string find(const std::string& id) const; };

std::string getDocument(const Request& request, const User& user, const DocumentStore& documents) {
    const auto document = documents.find(request.query("document_id"));
    return user.canRead(document) ? document : "forbidden";
}
