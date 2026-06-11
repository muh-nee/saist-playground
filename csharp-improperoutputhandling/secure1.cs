using System.Data.SqlClient;
using OpenAI.Chat;

var chatClient = new ChatClient("gpt-4o", Environment.GetEnvironmentVariable("OPENAI_API_KEY"));
var connection = new SqlConnection(Environment.GetEnvironmentVariable("DB_CONN"));

async Task<List<string>> SearchUsers(string userRequest)
{
    ChatCompletion completion = await chatClient.CompleteChatAsync(
        new SystemChatMessage("Extract only the search term from the request. Output plain text only."),
        new UserChatMessage(userRequest)
    );

    string searchTerm = completion.Content[0].Text.Trim();

    await connection.OpenAsync();
    using var cmd = new SqlCommand("SELECT name FROM users WHERE name = @Name", connection);
    cmd.Parameters.AddWithValue("@Name", searchTerm);
    using var reader = await cmd.ExecuteReaderAsync();

    var results = new List<string>();
    while (await reader.ReadAsync())
        results.Add(reader.GetString(0));
    return results;
}
