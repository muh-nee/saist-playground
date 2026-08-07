import express from "express";
import OpenAI from "openai";

const app = express();
app.use(express.json());
const openai = new OpenAI();
const DISCLAIMER = "AI-generated content. Please verify independently before acting on this information.";

app.post("/ask", async (req, res) => {
  const { question } = req.body;
  const completion = await openai.chat.completions.create({
    model: "gpt-4o",
    messages: [{ role: "user", content: question }],
  });
  const answer = completion.choices[0].message.content;
  res.json({ answer, disclaimer: DISCLAIMER });
});

app.listen(3000);
