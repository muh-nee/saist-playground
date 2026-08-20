#include <string>

struct Message {};
bool protobufHasExpectedType(const std::string& bytes);
Message decodeProtobuf(const std::string& bytes);
void dispatch(const Message& message);

void receive(const std::string& bytes) {
    if (!protobufHasExpectedType(bytes)) return;
    dispatch(decodeProtobuf(bytes));
}
