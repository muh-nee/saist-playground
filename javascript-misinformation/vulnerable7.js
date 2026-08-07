import Fastify from "fastify";
import OpenAI from "openai";

const fastify = Fastify();
const openai = new OpenAI();

fastify.post("/ask", async (request, reply) => {
  const { question } = request.body;
  const completion = await openai.chat.completions.create({
    model: "gpt-4o",
    messages: [{ role: "user", content: question }],
  });
  reply.send({ answer: completion.choices[0].message.content });
});

fastify.listen({ port: 3000 });
