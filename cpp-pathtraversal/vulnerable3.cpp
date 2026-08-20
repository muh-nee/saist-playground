#include <fstream>
#include <string>

void saveReport(const std::string& reportName, const std::string& content) {
    std::ofstream output("/srv/reports/" + reportName);
    output << content;
}
