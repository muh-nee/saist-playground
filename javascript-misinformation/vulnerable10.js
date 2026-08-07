import Fastify from "fastify";
import OpenAI from "openai";

const fastify = Fastify();
const openai = new OpenAI();

fastify.post("/ask", async (request, reply) => {
  const { question } = request.body;
  const response = await openai.responses.create({ model: "gpt-4o", input: question });
  return { answer: response.output_text };
});

fastify.listen({ port: 3000 });
