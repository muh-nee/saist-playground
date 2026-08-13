using System.Text.RegularExpressions;
using Microsoft.AspNetCore.Mvc;
using OpenAI.Chat;

[ApiController]
[Route("[controller]")]
public class SummaryController : ControllerBase
{
	[HttpGet]
	public async Task<IActionResult> GetSummary()
	{
		var client = new ChatClient(model: "gpt-4o-mini", apiKey: Environment.GetEnvironmentVariable("OPENAI_API_KEY"));
		var completion = await client.CompleteChatAsync(ChatMessage.CreateUserMessage("Summarize the latest AI news in Markdown."));
		string content = completion.Value.Content[0].Text;
		string sanitized = Regex.Replace(content, @"!\[.*?\]\(.*?\)", "", RegexOptions.Singleline);
		return Ok(new { content = sanitized });
	}
}
