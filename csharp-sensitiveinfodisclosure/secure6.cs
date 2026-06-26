using OpenAI.Chat;
using Microsoft.AspNetCore.Http;

public class SecureGenericUserInput
{
    private ChatClient _chatClient;
    private HttpRequest _request;

    public async Task<string> HandleQuery()
    {
        string userQuery = _request.Form["query"].ToString();
        ChatCompletion completion = await _chatClient.CompleteChatAsync(
            new[] { new UserChatMessage(userQuery) }
        );
        return completion.Content[0].Text;
    }
}
