import express from "express";
import { streamText } from "ai";
import { openai } from "@ai-sdk/openai";

const app = express();
app.use(express.json());
const DISCLAIMER = "AI-generated content — verify before use.\n\n";

app.post("/stream", async (req, res) => {
  const { question } = req.body;
  const result = streamText({ model: openai("gpt-4o"), prompt: question });
  res.setHeader("Content-Type", "text/plain");
  res.write(DISCLAIMER);
  for await (const chunk of result.textStream) {
    res.write(chunk);
  }
  res.end();
});

app.listen(3000);
