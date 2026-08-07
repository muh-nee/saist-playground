import express, { Request, Response } from "express";
import { ChatOpenAI } from "@langchain/openai";

const app = express();
app.use(express.json());
const model = new ChatOpenAI({ model: "gpt-4o" });

app.post("/ask", async (req: Request, res: Response) => {
  const { question } = req.body as { question: string };
  const result = await model.invoke(question);
  res.json({ answer: result.content });
});

app.listen(3000);
