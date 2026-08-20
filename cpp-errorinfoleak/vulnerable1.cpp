#include <exception>
#include <string>

struct Response { void send(int status, const std::string& body); };
void readPrivateRecord();

void handleRequest(Response& response) {
    try {
        readPrivateRecord();
    } catch (const std::exception& error) {
        response.send(500, error.what());
    }
}
