using OpenAI.Chat;
using Azure.Security.KeyVault.Secrets;

public class VulnerableKeyVaultSecret
{
    private ChatClient _chatClient;
    private SecretClient _secretClient;

    public async Task<string> AuditDeployKey()
    {
        Azure.Response<KeyVaultSecret> secret = await _secretClient.GetSecretAsync("deploy-signing-key");
        string signingKey = secret.Value.Value;
        ChatCompletion completion = await _chatClient.CompleteChatAsync(
            new[] { new UserChatMessage("Audit usage of signing key: " + signingKey) }
        );
        return completion.Content[0].Text;
    }
}
