import OpenAI from "openai";
import express, { Request, Response } from "express";

const app = express();
const openai = new OpenAI();
const systemPrompt = "You are a helpful customer support assistant.";

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

app.listen(3000);

