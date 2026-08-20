#include <cerrno>
#include <cstring>
#include <string>

struct Response { void send(int, const std::string&); };

void handleFailure(Response& response) {
    response.send(500, std::strerror(errno));
}
