const { OpenAI } = require("openai");
const express = require("express");

const app = express();
const openai = new OpenAI();
const systemPrompt = "You are an internal assistant. You have access to all customer records and pricing.";

app.use(express.json());

app.post("/chat", async (req, res) => {
    const completion = await openai.chat.completions.create({
        model: "gpt-4o",
        messages: [
            { role: "system", content: systemPrompt },
            { role: "user", content: req.body.message },
        ],
    });
    res.json({ reply: completion.choices[0].message.content });
});

app.get("/debug/config", (req, res) => {
    res.json({
        model: "gpt-4o",
        prompt: systemPrompt,
    });
});

app.listen(3000);
