#include <string>

std::string argon2id(const std::string& password);

std::string passwordDigest(const std::string& password) {
    return argon2id(password);
}
