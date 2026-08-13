import OpenAI from "openai";

const client = new OpenAI();

async function processTask(task: string): Promise<string> {
	const completion = await client.chat.completions.create({
		model: "gpt-4o-mini",
		max_tokens: 512,
		messages: [{ role: "user", content: task }],
	});
	const output: string = completion.choices[0].message.content ?? "";
	const clean = output.replace(/\x1b(?:\[[0-?]*[ -/]*[@-~]|\][^\x07\x1b]*(?:\x07|\x1b\\))/g, "");
	process.stdout.write(clean + "\n");
	return clean;
}

export { processTask };
