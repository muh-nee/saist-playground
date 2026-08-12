import OpenAI from "openai";

const client = new OpenAI();

async function processTask(task: string): Promise<string> {
	const completion = await client.chat.completions.create({
		model: "gpt-4o-mini",
		max_tokens: 512,
		messages: [{ role: "user", content: task }],
	});
	const output: string = completion.choices[0].message.content ?? "";
	process.stdout.write(output + "\n");
	return output;
}

export { processTask };
