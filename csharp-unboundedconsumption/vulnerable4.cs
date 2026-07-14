using Microsoft.SemanticKernel;
using Microsoft.SemanticKernel.Connectors.OpenAI;

public class vulnerable4
{
    private readonly Kernel _kernel;

    public vulnerable4(Kernel kernel)
    {
        _kernel = kernel;
    }

    public async Task<string> AnalyzeAsync(string prompt)
    {
        var settings = new OpenAIPromptExecutionSettings
        {
            Temperature = 0.2
        };
        var result = await _kernel.InvokePromptAsync(prompt,
            new KernelArguments(settings));
        return result.ToString();
    }
}
