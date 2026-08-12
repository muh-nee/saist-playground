import express from "express";
import OpenAI from "openai";

const app = express();
const client = new OpenAI();

app.get("/summary", async (req, res) => {
	const completion = await client.chat.completions.create({
		model: "gpt-4o-mini",
		max_tokens: 512,
		messages: [{ role: "user", content: "Summarize the latest AI news in Markdown." }],
	});
	const content: string = completion.choices[0].message.content ?? "";
	res.json({ content });
});

app.listen(3000);
