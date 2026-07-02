using Microsoft.SemanticKernel;
using System.Data.SqlClient;

public class SecureNonSecretConfig
{
    private Kernel _kernel;

    public async Task<string> GetRegionInfo()
    {
        string region = Environment.GetEnvironmentVariable("AWS_REGION");
        string environment = Environment.GetEnvironmentVariable("APP_ENV");
        string response = await _kernel.InvokePromptAsync(
            $"Describe the infrastructure topology for region {region} in the {environment} environment."
        );
        return response;
    }
}
