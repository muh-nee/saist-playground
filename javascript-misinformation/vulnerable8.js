import { Hono } from "hono";
import Anthropic from "@anthropic-ai/sdk";

const app = new Hono();
const anthropic = new Anthropic();

app.post("/ask", async (c) => {
  const { question } = await c.req.json();
  const msg = await anthropic.messages.create({
    model: "claude-opus-4-5",
    max_tokens: 1024,
    messages: [{ role: "user", content: question }],
  });
  return c.json({ answer: msg.content[0].text });
});

export default app;
