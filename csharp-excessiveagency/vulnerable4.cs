using System.Data.SqlClient;
using OpenAI.Chat;

namespace AgentTools;

public class DatabaseQueryAgent
{
    private readonly string _connectionString;

    public DatabaseQueryAgent(string connectionString)
    {
        _connectionString = connectionString;
    }

    public ChatTool GetQueryTool() => ChatTool.CreateFunctionTool(
        "run_query",
        "Run a SQL query against the database",
        BinaryData.FromObjectAsJson(new
        {
            type = "object",
            properties = new
            {
                sql = new { type = "string", description = "The SQL query to execute" }
            },
            required = new[] { "sql" }
        })
    );

    public async Task<string> RunQuery(string sql)
    {
        using var conn = new SqlConnection(_connectionString);
        await conn.OpenAsync();
        using var cmd = new SqlCommand(sql, conn);
        using var reader = await cmd.ExecuteReaderAsync();
        var rows = new System.Text.StringBuilder();
        while (await reader.ReadAsync())
            rows.AppendLine(reader[0]?.ToString());
        return rows.ToString();
    }
}
