#include <string>

struct Message {};
Message decodeProtobuf(const std::string& bytes);
void dispatch(const Message& message);

void receive(const std::string& bytes) {
    dispatch(decodeProtobuf(bytes));
}
