using System.Text.RegularExpressions;
using OpenAI.Chat;

var client = new ChatClient(model: "gpt-4o-mini", apiKey: Environment.GetEnvironmentVariable("OPENAI_API_KEY"));

var messages = new List<ChatMessage>
{
	ChatMessage.CreateUserMessage("Explain the latest AI developments.")
};

var completion = await client.CompleteChatAsync(messages);
string output = completion.Value.Content[0].Text;
string clean = Regex.Replace(output, @"\x1b(?:\[[0-?]*[ -/]*[@-~]|\][^\x07\x1b]*(?:\x07|\x1b\\))", "");
Console.WriteLine(clean);
