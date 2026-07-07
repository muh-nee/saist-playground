using Microsoft.Extensions.AI;
using System.ComponentModel;

namespace AgentTools;

public enum ReportEndpoint { Sales, Inventory, Users }

public class SafeReportFetcher
{
    private static readonly Dictionary<ReportEndpoint, string> EndpointMap = new()
    {
        [ReportEndpoint.Sales]     = "https://internal.example.com/api/reports/sales",
        [ReportEndpoint.Inventory] = "https://internal.example.com/api/reports/inventory",
        [ReportEndpoint.Users]     = "https://internal.example.com/api/reports/users"
    };

    private readonly HttpClient _http = new();

    public AIFunction GetReportTool()
    {
        return AIFunctionFactory.Create(
            ([Description("Report type to fetch")] ReportEndpoint report) =>
            {
                var url = EndpointMap[report];
                return _http.GetStringAsync(url);
            },
            "fetch_report",
            "Fetch an internal report by type"
        );
    }
}
