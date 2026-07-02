using OpenAI.Chat;
using System.Data.SqlClient;

public class VulnerableSqlExceptionMessage
{
    private ChatClient _chatClient;

    public async Task<string> DiagnoseConnection()
    {
        string dbPassword = Environment.GetEnvironmentVariable("DB_PASSWORD");
        string connectionString = $"Server=db.internal;Database=app;User Id=svc;Password={dbPassword}";
        try
        {
            using var conn = new SqlConnection(connectionString);
            await conn.OpenAsync();
            return "ok";
        }
        catch (SqlException ex)
        {
            ChatCompletion completion = await _chatClient.CompleteChatAsync(
                new[] { new UserChatMessage("Diagnose this DB error: " + ex.Message) }
            );
            return completion.Content[0].Text;
        }
    }
}
