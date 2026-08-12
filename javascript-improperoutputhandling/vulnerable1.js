const OpenAI = require("openai");

const client = new OpenAI();

async function processTask(task) {
	const completion = await client.chat.completions.create({
		model: "gpt-4o-mini",
		max_tokens: 512,
		messages: [{ role: "user", content: task }],
	});
	const output = completion.choices[0].message.content;
	process.stdout.write(output + "\n");
	return output;
}

module.exports = { processTask };
