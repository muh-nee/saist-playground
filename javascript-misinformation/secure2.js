import express from "express";
import Anthropic from "@anthropic-ai/sdk";

const app = express();
app.use(express.json());
const anthropic = new Anthropic();
const AI_DISCLAIMER = "Note: AI-generated content. Verify before use.\n\n";

app.post("/ask", async (req, res) => {
  const { question } = req.body;
  const msg = await anthropic.messages.create({
    model: "claude-opus-4-5",
    max_tokens: 1024,
    messages: [{ role: "user", content: question }],
  });
  const answer = msg.content[0].text;
  res.json({ answer: AI_DISCLAIMER + answer });
});

app.listen(3000);
