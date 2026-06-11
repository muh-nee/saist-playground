using Azure;
using Azure.AI.OpenAI;
using Dapper;
using System.Data.SqlClient;

var openAIClient = new OpenAIClient(
    new Uri(Environment.GetEnvironmentVariable("AZURE_OPENAI_ENDPOINT")),
    new AzureKeyCredential(Environment.GetEnvironmentVariable("AZURE_OPENAI_KEY"))
);
var connection = new SqlConnection(Environment.GetEnvironmentVariable("DB_CONN"));

var allowedTables = new HashSet<string> { "users", "orders", "products" };

async Task<IEnumerable<dynamic>> QueryTable(string userQuestion)
{
    var options = new ChatCompletionsOptions
    {
        Messages =
        {
            new ChatRequestSystemMessage("Reply with only the table name relevant to the question."),
            new ChatRequestUserMessage(userQuestion)
        }
    };

    Response<ChatCompletions> response = await openAIClient.GetChatCompletionsAsync("gpt-4o", options);
    string tableName = response.Value.Choices[0].Message.Content.Trim().ToLower();

    if (!allowedTables.Contains(tableName))
        throw new ArgumentException($"Unknown table: {tableName}");

    return await connection.QueryAsync(
        $"SELECT * FROM {tableName} WHERE active = @Active",
        new { Active = 1 }
    );
}
