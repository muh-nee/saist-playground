using Betalgo.Ranul.OpenAI.Managers;
using Betalgo.Ranul.OpenAI.ObjectModels.RequestModels;
using Betalgo.Ranul.OpenAI.ObjectModels;
using System.Data.SqlClient;

public class VulnerableBetalgoOpenAI
{
    private IOpenAIService _openAIService;
    private SqlDataReader _reader;

    public async Task<string> ReviewAccount()
    {
        string email = (string)_reader["email"];
        string creditCard = (string)_reader["credit_card"];
        var completionResult = await _openAIService.ChatCompletion.CreateCompletion(
            new ChatCompletionCreateRequest
            {
                Messages = new List<ChatMessage>
                {
                    ChatMessage.FromUser($"Review account for {email} (card: {creditCard})")
                },
                Model = Models.Gpt_4o
            }
        );
        return completionResult.Choices[0].Message.Content;
    }
}
