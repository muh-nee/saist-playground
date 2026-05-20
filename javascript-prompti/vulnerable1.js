const OpenAI = require("openai");
const express = require("express");
const openai = new OpenAI();
const app = express();

app.post("/ask", async (req, res) => {
	const reply = await openai.chat.completions.create({
		model: "gpt-4",
		messages: [
			{ role: "user", content: `You are a helpful assistant. ${req.body.userQuestion}` },
		],
	});
	res.json(reply);
});
