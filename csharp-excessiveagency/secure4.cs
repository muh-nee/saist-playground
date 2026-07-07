using System.Data.SqlClient;
using OpenAI.Chat;

namespace AgentTools;

public class SafeDatabaseAgent
{
    private readonly string _connectionString;

    public SafeDatabaseAgent(string connectionString)
    {
        _connectionString = connectionString;
    }

    public ChatTool GetQueryTool() => ChatTool.CreateFunctionTool(
        "get_user",
        "Look up a user by username",
        BinaryData.FromObjectAsJson(new
        {
            type = "object",
            properties = new
            {
                username = new { type = "string", description = "Username to look up" }
            },
            required = new[] { "username" }
        })
    );

    public async Task<string> GetUser(string username)
    {
        const string sql = "SELECT id, display_name FROM users WHERE username = @username";
        using var conn = new SqlConnection(_connectionString);
        await conn.OpenAsync();
        using var cmd = new SqlCommand(sql, conn);
        cmd.Parameters.AddWithValue("@username", username);
        using var reader = await cmd.ExecuteReaderAsync();
        return reader.HasRows ? reader["display_name"]?.ToString() ?? "" : "not found";
    }
}
