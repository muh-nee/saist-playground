import OpenAI from "openai";
import express, { Request, Response } from "express";

const app = express();
const openai = new OpenAI();
const systemPrompt = "Internal assistant configuration.";

app.use(express.json());

app.get("/config", (req: Request, res: Response) => {
    if (!(req as any).user?.isAdmin) {
        return res.status(403).json({ error: "Forbidden" });
    }
    res.json({ prompt: systemPrompt });
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

