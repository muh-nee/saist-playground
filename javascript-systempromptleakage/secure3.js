const { OpenAI } = require("openai");
const express = require("express");

const app = express();
const openai = new OpenAI();
const systemPrompt = "Confidential business instructions for the support bot.";

app.use(express.json());

app.post("/chat", async (req, res) => {
    const completion = await openai.chat.completions.create({
        model: "gpt-4o",
        messages: [
            { role: "system", content: systemPrompt },
            { role: "user", content: req.body.message },
        ],
    });
    res.json({ reply: completion.choices[0].message.content, disclaimer: "AI-generated content. Verify independently." });
});

app.listen(3000);
