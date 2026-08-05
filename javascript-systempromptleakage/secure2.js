const { OpenAI } = require("openai");
const express = require("express");

const app = express();
const openai = new OpenAI();
const systemPrompt = "Internal assistant with access to support tooling.";

app.use(express.json());

function requireAdmin(req, res, next) {
    const token = req.headers.authorization;
    if (!token || !isValidAdminToken(token)) {
        return res.status(403).json({ error: "Forbidden" });
    }
    next();
}

app.get("/admin/prompt", requireAdmin, (req, res) => {
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
