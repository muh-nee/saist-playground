#include <cerrno>
#include <cstring>
#include <string>

struct Response { void send(int, const std::string&); };
void logError(const std::string& error);

void handleFailure(Response& response) {
    logError(std::strerror(errno));
    response.send(500, "Unable to process the request");
}
