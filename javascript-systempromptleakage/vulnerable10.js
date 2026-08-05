const { OpenAI } = require("openai");
const express = require("express");

const app = express();
const openai = new OpenAI();
const systemPrompt = "Internal support agent. Do not disclose internal tooling to users.";

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

app.get("/config", (req, res) => {
    res.send("Active prompt: " + systemPrompt);
});

app.listen(3000);
