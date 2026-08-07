import express, { Request, Response } from "express";
import { GoogleGenerativeAI } from "@google/generative-ai";

const app = express();
app.use(express.json());
const genAI = new GoogleGenerativeAI(process.env.GEMINI_API_KEY!);

app.post("/ask", async (req: Request, res: Response) => {
  const { question } = req.body as { question: string };
  const model = genAI.getGenerativeModel({ model: "gemini-1.5-pro" });
  const result = await model.generateContent(question);
  const answer = result.response.text();
  res.json({ answer });
});

app.listen(3000);
