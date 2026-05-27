import Anthropic from "@anthropic-ai/sdk";

const anthropic = new Anthropic();

export async function askClaude(userInput: string) {
	return anthropic.messages.create({
		model: "claude-3-5-sonnet-latest",
		system: `You are an assistant. The user said: ${userInput}. Now answer their question.`,
		messages: [{ role: "user", content: "Proceed." }],
		max_tokens: 1024,
	});
}
