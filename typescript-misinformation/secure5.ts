import express, { Request, Response } from "express";
import OpenAI from "openai";

const app = express();
app.use(express.json());
const openai = new OpenAI();
const pendingAnswers = new Map<string, string>();

app.post("/ask", async (req: Request, res: Response) => {
  const { question } = req.body as { question: string };
  const completion = await openai.chat.completions.create({
    model: "gpt-4o",
    messages: [{ role: "user", content: question }],
  });
  const answer = completion.choices[0].message.content!;
  pendingAnswers.set("pending-1", answer);
  res.json({ status: "pending_review", id: "pending-1" });
});

app.listen(3000);
