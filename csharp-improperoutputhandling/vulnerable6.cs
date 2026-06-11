using Microsoft.CodeAnalysis.CSharp.Scripting;
using Microsoft.Extensions.AI;

IChatClient chatClient = new OpenAIChatClient(
    new System.ClientModel.ApiKeyCredential(Environment.GetEnvironmentVariable("OPENAI_API_KEY")),
    "gpt-4o"
);

async Task<object> EvaluateExpression(string userRequest)
{
    var response = await chatClient.GetResponseAsync(
        $"Output only the C# expression that evaluates: {userRequest}"
    );

    string code = response.Text;
    return await CSharpScript.EvaluateAsync(code);
}
