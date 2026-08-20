#include <array>
#include <string>

int RAND_bytes(unsigned char* buffer, int length);

std::string resetToken() {
    std::array<unsigned char, 32> bytes{};
    RAND_bytes(bytes.data(), bytes.size());
    return std::string(bytes.begin(), bytes.end());
}
