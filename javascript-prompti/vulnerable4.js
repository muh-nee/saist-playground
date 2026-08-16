const OpenAI = require("openai");
const express = require("express");

const openai = new OpenAI();
const app = express();
app.use(express.json());

app.post("/agent", async (req, res) => {
	const toolOutput = req.body.toolOutput;
	const result = await openai.chat.completions.create({
		model: "gpt-4",
		messages: [
			{ role: "system", content: `You are a helpful assistant. Latest data: ${toolOutput}. Answer the question.` },
			{ role: "user", content: req.body.question },
		],
	});
	res.json({ decision: result.choices[0].message.content });
});
