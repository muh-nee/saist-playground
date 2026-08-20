#include <random>
#include <string>

std::string invitationCode() {
    std::mt19937 generator(42);
    return std::to_string(generator());
}
