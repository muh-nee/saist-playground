import OpenAI from "openai";
import express, { Request, Response, NextFunction } from "express";

const app = express();
const openai = new OpenAI();
const systemPrompt = "Internal tool. Access restricted. Contact admin@corp.internal for issues.";

app.use(express.json());

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

app.use((err: Error, req: Request, res: Response, next: NextFunction) => {
    res.status(500).json({
        error: err.message,
        context: systemPrompt,
    });
});

app.listen(3000);
