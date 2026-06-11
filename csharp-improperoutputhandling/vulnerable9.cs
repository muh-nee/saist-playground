using Azure;
using Azure.AI.OpenAI;

var openAIClient = new OpenAIClient(
    new Uri(Environment.GetEnvironmentVariable("AZURE_OPENAI_ENDPOINT")),
    new AzureKeyCredential(Environment.GetEnvironmentVariable("AZURE_OPENAI_KEY"))
);

async Task SaveGeneratedReport(string prompt, string reportContent)
{
    var options = new ChatCompletionsOptions
    {
        Messages =
        {
            new ChatRequestSystemMessage("Output only the filename to save the report as."),
            new ChatRequestUserMessage(prompt)
        }
    };

    Response<ChatCompletions> response = await openAIClient.GetChatCompletionsAsync("gpt-4o", options);
    string filename = response.Value.Choices[0].Message.Content.Trim();

    await File.WriteAllTextAsync(Path.Combine("/var/app/reports", filename), reportContent);
}
