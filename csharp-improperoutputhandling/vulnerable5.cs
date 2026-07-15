using System.Management.Automation;
using Microsoft.SemanticKernel;
using Microsoft.SemanticKernel.ChatCompletion;
using Microsoft.SemanticKernel.Connectors.OpenAI;

var kernel = Kernel.CreateBuilder()
    .AddOpenAIChatCompletion("gpt-4o", Environment.GetEnvironmentVariable("OPENAI_API_KEY"))
    .Build();

var chatService = kernel.GetRequiredService<IChatCompletionService>();

async Task AutomateAdminTask(string taskDescription)
{
    var history = new ChatHistory();
    history.AddSystemMessage("Output only the PowerShell script to complete the task.");
    history.AddUserMessage(taskDescription);

    ChatMessageContent message = await chatService.GetChatMessageContentAsync(history, new OpenAIPromptExecutionSettings { MaxTokens = 1024 });
    string script = message.Content;

    using var ps = PowerShell.Create();
    ps.AddScript(script);
    ps.Invoke();
}
