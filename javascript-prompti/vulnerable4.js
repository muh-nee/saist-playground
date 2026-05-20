const OpenAI = require("openai");
const openai = new OpenAI();

async function agentLoop(toolOutput) {
	return openai.chat.completions.create({
		model: "gpt-4",
		messages: [
			{ role: "system", content: "Continue the task." },
			{ role: "user", content: `Tool result: ${toolOutput}. Decide next step.` },
		],
	});
}
