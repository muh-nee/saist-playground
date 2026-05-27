import OpenAI from "openai";

const openai = new OpenAI();

export async function agentLoop(toolOutput: string) {
	return openai.chat.completions.create({
		model: "gpt-4",
		messages: [
			{ role: "system", content: "Continue the task." },
			{ role: "user", content: `Tool result: ${toolOutput}. Decide next step.` },
		],
	});
}
