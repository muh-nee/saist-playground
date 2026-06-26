using Amazon.BedrockRuntime;
using Amazon.BedrockRuntime.Model;

public class VulnerableBedrockConverse
{
    private AmazonBedrockRuntimeClient _bedrockRuntimeClient;

    public async Task<string> DiagnoseError()
    {
        string dbPassword = Environment.GetEnvironmentVariable("DB_PASSWORD");
        var request = new ConverseRequest
        {
            ModelId = "anthropic.claude-3-5-sonnet-20241022-v2:0",
            Messages = new List<Message>
            {
                new Message
                {
                    Role = ConversationRole.User,
                    Content = new List<ContentBlock>
                    {
                        new ContentBlock { Text = "Debug this DB error: password=" + dbPassword }
                    }
                }
            }
        };
        var response = await _bedrockRuntimeClient.ConverseAsync(request);
        return response.Output.Message.Content[0].Text;
    }
}
