const { OpenAI } = require("openai");
const express = require("express");

const app = express();
const openai = new OpenAI();
const systemPrompt = "Internal assistant configuration.";

app.use(express.json());

app.get("/config", (req, res) => {
    if (!req.user?.isAdmin) {
        return res.status(403).json({ error: "Forbidden" });
    }
    res.json({ prompt: systemPrompt });
});

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

app.listen(3000);
