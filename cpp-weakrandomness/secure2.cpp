#include <array>
#include <string>

int RAND_bytes(unsigned char* buffer, int length);

std::string nonce() {
    std::array<unsigned char, 16> bytes{};
    if (RAND_bytes(bytes.data(), static_cast<int>(bytes.size())) != 1) return {};
    return std::string(bytes.begin(), bytes.end());
}
