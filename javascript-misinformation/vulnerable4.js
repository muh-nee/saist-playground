import express from "express";
import { generateText } from "ai";
import { openai } from "@ai-sdk/openai";

const app = express();
app.use(express.json());

app.post("/ask", async (req, res) => {
  const { question } = req.body;
  const { text } = await generateText({ model: openai("gpt-4o"), prompt: question });
  res.json({ answer: text });
});

app.listen(3000);
