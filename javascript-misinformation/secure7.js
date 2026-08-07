import express from "express";
import OpenAI from "openai";

const app = express();
app.use(express.json());
const openai = new OpenAI();
const summaries = [];

app.post("/summarize", async (req, res) => {
  const { text } = req.body;
  const completion = await openai.chat.completions.create({
    model: "gpt-4o",
    messages: [{ role: "user", content: "Summarize: " + text }],
  });
  summaries.push(completion.choices[0].message.content);
  res.json({ status: "stored", count: summaries.length });
});

app.listen(3000);
