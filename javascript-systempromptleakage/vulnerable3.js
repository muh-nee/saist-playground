const { OpenAI } = require("openai");
const express = require("express");

const app = express();
const openai = new OpenAI();
const systemInstructions = "You assist with HR queries. Employee salary data is available to you.";

app.use(express.json());

app.post("/chat", async (req, res) => {
    console.log(`[DEBUG] systemInstructions=${systemInstructions}`);
    const completion = await openai.chat.completions.create({
        model: "gpt-4o",
        messages: [
            { role: "system", content: systemInstructions },
            { role: "user", content: req.body.message },
        ],
    });
    res.json({ reply: completion.choices[0].message.content, disclaimer: "AI-generated content. Verify independently." });
});

app.listen(3000);
