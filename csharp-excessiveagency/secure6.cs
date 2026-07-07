using System.Data.SqlClient;
using ModelContextProtocol.Server;
using System.ComponentModel;

namespace AgentTools;

[McpServerToolType]
public class SafeUserLookup
{
    private readonly string _connectionString;

    public SafeUserLookup(string connectionString)
    {
        _connectionString = connectionString;
    }

    [McpServerTool(Name = "lookup_user")]
    [Description("Look up a user record by ID")]
    public async Task<string> LookupUser(int userId)
    {
        const string sql = "SELECT username, email FROM users WHERE id = @id";
        using var conn = new SqlConnection(_connectionString);
        await conn.OpenAsync();
        using var cmd = new SqlCommand(sql, conn);
        cmd.Parameters.AddWithValue("@id", userId);
        using var reader = await cmd.ExecuteReaderAsync();
        if (!reader.HasRows) return "user not found";
        await reader.ReadAsync();
        return $"{reader["username"]} <{reader["email"]}>";
    }
}
