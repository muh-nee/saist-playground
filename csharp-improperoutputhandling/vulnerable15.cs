using Microsoft.AspNetCore.Http;
using Microsoft.Extensions.AI;

IChatClient chatClient = new OpenAIChatClient(
    new System.ClientModel.ApiKeyCredential(Environment.GetEnvironmentVariable("OPENAI_API_KEY")),
    "gpt-5.2"
);

async Task HandlePostAction(HttpContext context, string userRequest)
{
    var response = await chatClient.GetResponseAsync(
        $"Return only the URL the user should be redirected to after: {userRequest}"
    );

    string redirectUrl = response.Text.Trim();
    context.Response.Redirect(redirectUrl);
}

