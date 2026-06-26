using OpenAI.Chat;
using System.Data.SqlClient;

public class VulnerableHelperMethod
{
    private ChatClient _chatClient;
    private SqlDataReader _reader;

    public async Task<string> HandleSupportTicket()
    {
        string name = (string)_reader["name"];
        string email = (string)_reader["email"];
        string plan = (string)_reader["subscription_tier"];
        string prompt = BuildSupportContext(name, email, plan);
        ChatCompletion completion = await _chatClient.CompleteChatAsync(
            new[] { new UserChatMessage(prompt) }
        );
        return completion.Content[0].Text;
    }

    private string BuildSupportContext(string name, string email, string plan)
    {
        return $"Customer: {name}, email: {email}, plan: {plan}";
    }
}
