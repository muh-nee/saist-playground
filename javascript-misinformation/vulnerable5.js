import { Hono } from "hono";
import { streamText } from "ai";
import { openai } from "@ai-sdk/openai";

const app = new Hono();

app.post("/ask", async (c) => {
  const { question } = await c.req.json();
  const result = streamText({ model: openai("gpt-4o"), prompt: question });
  return result.toDataStreamResponse();
});

export default app;
