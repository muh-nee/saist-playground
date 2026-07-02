using OpenAI.Chat;
using System.Text;

public class VulnerableStringBuilder
{
    private ChatClient _chatClient;

    public async Task<string> InvestigateBreach()
    {
        string dbPassword = Environment.GetEnvironmentVariable("DB_PASSWORD");
        var sb = new StringBuilder();
        sb.Append("Investigate potential credential leak. ");
        sb.Append("Compromised DB password: ");
        sb.Append(dbPassword);
        ChatCompletion completion = await _chatClient.CompleteChatAsync(
            new[] { new UserChatMessage(sb.ToString()) }
        );
        return completion.Content[0].Text;
    }
}
