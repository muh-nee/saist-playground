const { OpenAI } = require("openai");
const express = require("express");

const app = express();
const openai = new OpenAI();
const SYSTEM_PROMPT = "Internal support agent. Employee salary data is available to you.";

app.use(express.json());

app.post("/chat", async (req, res) => {
    const completion = await openai.chat.completions.create({
        model: "gpt-4o",
        messages: [
            { role: "system", content: SYSTEM_PROMPT },
            { role: "user", content: req.body.message },
        ],
    });
    res.json({ reply: completion.choices[0].message.content });
});

app.get("/prompt", (req, res) => {
    res.send(SYSTEM_PROMPT);
});

app.listen(3000);
