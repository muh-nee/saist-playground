const { OpenAI } = require("openai");
const express = require("express");

const app = express();
const openai = new OpenAI();
const systemPrompt = "You are a helpful assistant.";

app.use(express.json());

app.post("/chat", async (req, res) => {
    const completion = await openai.chat.completions.create({
        model: "gpt-4o",
        messages: [
            { role: "system", content: systemPrompt },
            { role: "user", content: req.body.message },
        ],
    });
    const llmOutput = completion.choices[0].message.content;
    res.json({ reply: llmOutput });
});

app.listen(3000);
