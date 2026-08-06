import OpenAI from "openai";
import express, { Request, Response, NextFunction } from "express";

const app = express();
const openai = new OpenAI();
const systemPrompt = "Internal assistant with access to support tooling.";

app.use(express.json());

function requireAdmin(req: Request, res: Response, next: NextFunction) {
    const token = req.headers.authorization;
    if (!token || !isValidAdminToken(token)) {
        return res.status(403).json({ error: "Forbidden" });
    }
    next();
}

app.get("/admin/prompt", requireAdmin, (req: Request, res: Response) => {
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

