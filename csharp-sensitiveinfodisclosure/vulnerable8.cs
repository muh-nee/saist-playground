using Google.Cloud.AIPlatform.V1;

public class VulnerableVertexAI
{
    private PredictionServiceClient _predictionServiceClient;
    private UserEntity _user;

    public async Task<string> SummarizeUser()
    {
        string email = _user.Email;
        string ssn = _user.Ssn;
        var generateRequest = new GenerateContentRequest
        {
            Model = "projects/my-project/locations/us-central1/publishers/google/models/gemini-1.5-pro"
        };
        generateRequest.Contents.Add(new Content
        {
            Parts = { new Part { Text = $"Summarize activity for {email} (SSN: {ssn})" } }
        });
        var generateResponse = await _predictionServiceClient.GenerateContentAsync(generateRequest);
        return generateResponse.Candidates[0].Content.Parts[0].Text;
    }
}

public class UserEntity { public string Email { get; set; } public string Ssn { get; set; } }
