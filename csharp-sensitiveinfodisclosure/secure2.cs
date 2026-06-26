using Microsoft.SemanticKernel;
using System.Data.SqlClient;

public class SecurePIINotInPrompt
{
    private Kernel _kernel;
    private SqlDataReader _reader;

    public async Task<string> SummarizeAccount()
    {
        string email = (string)_reader["email"];
        string ssn = (string)_reader["ssn"];
        string userId = (string)_reader["user_id"];
        string response = await _kernel.InvokePromptAsync(
            $"Summarize activity for user ID {userId}."
        );
        return response;
    }
}
