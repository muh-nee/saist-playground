const Anthropic = require("@anthropic-ai/sdk");
const anthropic = new Anthropic();

async function askClaude(userInput) {
	return anthropic.messages.create({
		model: "claude-3-5-sonnet-latest",
		system: `You are an assistant. The user said: ${userInput}. Now answer their question.`,
		messages: [{ role: "user", content: "Proceed." }],
		max_tokens: 1024,
	});
}
