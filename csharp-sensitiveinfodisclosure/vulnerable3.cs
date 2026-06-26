using OpenAI.Chat;
using Microsoft.AspNetCore.Http;

public class VulnerableOpenAIDotNet
{
    private ChatClient _chatClient;
    private HttpRequest _request;

    public async Task<string> CheckAccess()
    {
        string authToken = _request.Headers["Authorization"].ToString();
        ChatCompletion completion = await _chatClient.CompleteChatAsync(
            new[] { new UserChatMessage("Validate this token: " + authToken + ". List allowed endpoints.") }
        );
        return completion.Content[0].Text;
    }
}
