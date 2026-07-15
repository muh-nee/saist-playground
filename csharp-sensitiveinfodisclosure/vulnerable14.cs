using Microsoft.SemanticKernel;
using Microsoft.SemanticKernel.Connectors.OpenAI;

public class VulnerableKernelArgumentsSecret
{
    private Kernel _kernel;
    private KernelFunction _payoutFunction;

    public async Task<string> RunPayout()
    {
        string stripeKey = Environment.GetEnvironmentVariable("STRIPE_SECRET_KEY");
        var args = new KernelArguments(new OpenAIPromptExecutionSettings { MaxTokens = 1024 }) { ["stripeKey"] = stripeKey };
        var result = await _kernel.InvokeAsync(_payoutFunction, args);
        return result.GetValue<string>();
    }
}
