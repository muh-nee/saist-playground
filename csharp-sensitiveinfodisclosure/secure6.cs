using OpenAI.Chat;
using System.Data.SqlClient;

public class SecureHelperDropsPII
{
    private ChatClient _chatClient;
    private SqlDataReader _reader;

    public async Task<string> HandleSupportTicket()
    {
        string name = (string)_reader["name"];
        string email = (string)_reader["email"];
        string region = (string)_reader["region"];
        string prompt = BuildPrompt(name, email, region);
        ChatCompletion completion = await _chatClient.CompleteChatAsync(
            new[] { new UserChatMessage(prompt) }
        );
        return completion.Content[0].Text;
    }

    private string BuildPrompt(string name, string email, string region)
    {
        return $"Customer region: {region}.";
    }
}
