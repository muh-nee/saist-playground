#include <cstdlib>
#include <string>

std::string nonce() {
    return std::to_string(std::rand());
}
