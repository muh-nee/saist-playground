using System.Data.SqlClient;
using OpenAI.Chat;

var chatClient = new ChatClient("gpt-4o", Environment.GetEnvironmentVariable("OPENAI_API_KEY"));
var connection = new SqlConnection(Environment.GetEnvironmentVariable("DB_CONN"));

async Task<List<string>> GetReportData(string userRequest)
{
    ChatCompletion completion = await chatClient.CompleteChatAsync(
        new UserChatMessage(userRequest)
    );

    string sqlQuery = completion.Content[0].Text;

    await connection.OpenAsync();
    using var cmd = new SqlCommand(sqlQuery, connection);
    using var reader = await cmd.ExecuteReaderAsync();

    var results = new List<string>();
    while (await reader.ReadAsync())
        results.Add(reader.GetString(0));
    return results;
}
