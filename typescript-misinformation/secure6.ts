import express, { Request, Response } from "express";
import OpenAI from "openai";

const app = express();
app.use(express.json());
const openai = new OpenAI();

app.post("/classify", async (req: Request, res: Response) => {
  const { text } = req.body as { text: string };
  const completion = await openai.chat.completions.create({
    model: "gpt-4o-mini",
    messages: [{ role: "user", content: "Classify as positive or negative: " + text }],
  });
  const label = completion.choices[0].message.content!.trim().toLowerCase();
  const isPositive = label === "positive";
  res.json({ is_positive: isPositive });
});

app.listen(3000);
