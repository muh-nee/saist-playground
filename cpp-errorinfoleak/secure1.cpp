#include <exception>
#include <string>

struct Response { void send(int status, const std::string& body); };
void logInternalError(const std::exception& error);
void readPrivateRecord();

void handleRequest(Response& response) {
    try {
        readPrivateRecord();
    } catch (const std::exception& error) {
        logInternalError(error);
        response.send(500, "Internal server error");
    }
}
