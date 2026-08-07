import Fastify from "fastify";
import { generateText } from "ai";
import { openai } from "@ai-sdk/openai";

const fastify = Fastify();

fastify.post<{ Body: { question: string } }>("/ask", async (request, reply) => {
  const { question } = request.body;
  const { text } = await generateText({ model: openai("gpt-4o"), prompt: question });
  return { answer: text };
});

fastify.listen({ port: 3000 });
