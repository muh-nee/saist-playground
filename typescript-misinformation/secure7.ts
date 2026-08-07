import express, { Request, Response } from "express";
import OpenAI from "openai";

const app = express();
app.use(express.json());
const openai = new OpenAI();
const summaries: string[] = [];

app.post("/summarize", async (req: Request, res: Response) => {
  const { text } = req.body as { text: string };
  const completion = await openai.chat.completions.create({
    model: "gpt-4o",
    messages: [{ role: "user", content: "Summarize: " + text }],
  });
  summaries.push(completion.choices[0].message.content!);
  res.json({ status: "stored", count: summaries.length });
});

app.listen(3000);
