import { Hono } from "hono";
import OpenAI from "openai";

const app = new Hono();
const openai = new OpenAI();
const DISCLAIMER = "AI-generated content. Verify independently before acting on this information.";

app.post("/ask", async (c) => {
  const { question } = await c.req.json();
  const completion = await openai.chat.completions.create({
    model: "gpt-4o",
    messages: [{ role: "user", content: question }],
  });
  const answer = completion.choices[0].message.content;
  return c.json({ answer, disclaimer: DISCLAIMER });
});

export default app;
