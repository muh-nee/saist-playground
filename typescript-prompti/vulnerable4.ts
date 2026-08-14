import OpenAI from "openai";
import express from "express";

const openai = new OpenAI();
const app = express();
app.use(express.json());

app.post("/agent", async (req, res) => {
	const toolOutput: string = req.body.toolOutput;
	const result = await openai.chat.completions.create({
		model: "gpt-4",
		messages: [
			{ role: "system", content: "Continue the task." },
			{ role: "user", content: `Tool result: ${toolOutput}. Decide next step.` },
		],
	});
	res.json({ decision: result.choices[0].message.content });
});
