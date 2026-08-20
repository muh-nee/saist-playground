#include <string>

std::string md5(const std::string& value);

std::string passwordDigest(const std::string& password) {
    return md5(password);
}
