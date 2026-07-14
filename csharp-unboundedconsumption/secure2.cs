using Microsoft.SemanticKernel;
using Microsoft.SemanticKernel.Connectors.OpenAI;

public class secure2
{
    private readonly Kernel _kernel;

    public secure2(Kernel kernel)
    {
        _kernel = kernel;
    }

    public async Task<string> SummarizeAsync(string text)
    {
        var settings = new OpenAIPromptExecutionSettings
        {
            MaxTokens = 1024
        };
        var result = await _kernel.InvokePromptAsync($"Summarize: {text}",
            new KernelArguments(settings));
        return result.ToString();
    }
}
