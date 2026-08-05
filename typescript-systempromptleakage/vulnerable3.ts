import OpenAI from "openai";
import express, { Request, Response } from "express";

const app = express();
const openai = new OpenAI();
const systemInstructions = "You assist with HR queries. Employee salary data is available to you.";

app.use(express.json());

app.post("/chat", async (req: Request, res: Response) => {
    console.log(`[DEBUG] systemInstructions=${systemInstructions}`);
    const completion = await openai.chat.completions.create({
        model: "gpt-4o",
        messages: [
            { role: "system", content: systemInstructions },
            { role: "user", content: req.body.message as string },
        ],
    });
    res.json({ reply: completion.choices[0].message.content });
});

app.listen(3000);
