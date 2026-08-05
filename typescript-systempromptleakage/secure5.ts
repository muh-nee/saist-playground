import OpenAI from "openai";
import express, { Request, Response } from "express";

const app = express();
const openai = new OpenAI();
const systemPrompt = "Proprietary assistant instructions.";

app.use(express.json());

app.get("/debug/prompt-info", (req: Request, res: Response) => {
    res.json({
        promptLength: systemPrompt.length,
        promptPreview: "[REDACTED]",
    });
});

app.post("/chat", async (req: Request, res: Response) => {
    const completion = await openai.chat.completions.create({
        model: "gpt-4o",
        messages: [
            { role: "system", content: systemPrompt },
            { role: "user", content: req.body.message as string },
        ],
    });
    res.json({ reply: completion.choices[0].message.content });
});

app.listen(3000);
