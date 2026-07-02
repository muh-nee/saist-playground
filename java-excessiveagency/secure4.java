package main;

import dev.langchain4j.agent.tool.Tool;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;

class ReportTools {
    private static final Map<String, String> REPORTS = Map.of(
            "q1_sales", "/var/app/reports/q1_sales.csv",
            "q2_sales", "/var/app/reports/q2_sales.csv",
            "q3_sales", "/var/app/reports/q3_sales.csv"
    );

    @Tool("Retrieve a quarterly sales report")
    public String getReport(String reportName) throws IOException {
        String path = REPORTS.get(reportName);
        if (path == null) {
            throw new IllegalArgumentException("unknown report");
        }
        return Files.readString(Path.of(path));
    }
}
