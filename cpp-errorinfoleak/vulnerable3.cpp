#include <string>

struct Response { void send(int, const std::string&); };

void fileError(Response& response, const std::string& path) {
    response.send(404, "Unable to open internal file " + path);
}
