using Microsoft.SemanticKernel;
using System.Data.SqlClient;

public class VulnerableSemanticKernel
{
    private Kernel _kernel;
    private SqlDataReader _reader;

    public async Task<string> SummarizeAccount()
    {
        string email = (string)_reader["email"];
        string ssn = (string)_reader["ssn"];
        string response = await _kernel.InvokePromptAsync(
            $"Summarize account activity for {email} (SSN: {ssn})."
        );
        return response;
    }
}
