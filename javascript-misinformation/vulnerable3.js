import express from "express";
import { ChatOpenAI } from "@langchain/openai";

const app = express();
app.use(express.json());
const model = new ChatOpenAI({ model: "gpt-4o" });

app.post("/ask", async (req, res) => {
  const { question } = req.body;
  const result = await model.invoke(question);
  res.json({ answer: result.content });
});

app.listen(3000);
