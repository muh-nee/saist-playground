using Azure;
using Azure.AI.OpenAI;
using Dapper;
using System.Data.SqlClient;

var openAIClient = new OpenAIClient(
    new Uri(Environment.GetEnvironmentVariable("AZURE_OPENAI_ENDPOINT")),
    new AzureKeyCredential(Environment.GetEnvironmentVariable("AZURE_OPENAI_KEY"))
);
var connection = new SqlConnection(Environment.GetEnvironmentVariable("DB_CONN"));

async Task<IEnumerable<dynamic>> SearchOrders(string userRequest)
{
    var options = new ChatCompletionsOptions
    {
        Messages =
        {
            new ChatRequestSystemMessage("Extract only the customer name from the request. Output plain text only."),
            new ChatRequestUserMessage(userRequest)
        }
    };

    Response<ChatCompletions> response = await openAIClient.GetChatCompletionsAsync("gpt-4o", options);
    string customerName = response.Value.Choices[0].Message.Content.Trim();

    return await connection.QueryAsync(
        "SELECT * FROM orders WHERE customer_name = @Name",
        new { Name = customerName }
    );
}
