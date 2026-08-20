#include <cstdio>
#include <string>

void upload(const std::string& fileName) {
    FILE* file = fopen(("/srv/uploads/" + fileName).c_str(), "wb");
    fclose(file);
}
