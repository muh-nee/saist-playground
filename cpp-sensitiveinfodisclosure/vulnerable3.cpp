#include <string>

struct Response { void send(const std::string&); };

void debug(Response& response, const std::string& authorizationHeader) {
    response.send("Authorization: " + authorizationHeader);
}
