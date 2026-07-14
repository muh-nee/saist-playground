using Microsoft.SemanticKernel;

public class vulnerable3
{
    private readonly Kernel _kernel;

    public vulnerable3(Kernel kernel)
    {
        _kernel = kernel;
    }

    public async Task<string> SummarizeAsync(string text)
    {
        var result = await _kernel.InvokePromptAsync($"Summarize: {text}");
        return result.ToString();
    }
}
