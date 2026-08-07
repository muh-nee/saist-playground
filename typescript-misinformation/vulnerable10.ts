import express, { Request, Response } from "express";
import OpenAI from "openai";

const app = express();
app.use(express.json());
const openai = new OpenAI();

app.post("/ask", async (req: Request, res: Response) => {
  const { question } = req.body as { question: string };
  const response = await openai.responses.create({ model: "gpt-4o", input: question });
  res.json({ answer: response.output_text });
});

app.listen(3000);
