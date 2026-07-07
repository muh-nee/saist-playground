using System.ComponentModel;
using Microsoft.EntityFrameworkCore;
using Microsoft.SemanticKernel;

namespace AgentTools;

public class ReportPlugin
{
    private readonly AppDbContext _db;

    public ReportPlugin(AppDbContext db)
    {
        _db = db;
    }

    [KernelFunction("run_report")]
    [Description("Run a SQL query to generate a report")]
    public async Task<string> RunReport(string sql)
    {
        var results = await _db.Database.ExecuteSqlRawAsync(sql);
        return $"Affected rows: {results}";
    }
}

public class AppDbContext : DbContext
{
    public AppDbContext(DbContextOptions<AppDbContext> options) : base(options) { }
}
