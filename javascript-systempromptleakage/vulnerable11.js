const Fastify = require("fastify");
const { OpenAI } = require("openai");

const fastify = Fastify();
const openai = new OpenAI();
const systemPrompt = "Proprietary assistant instructions. Not for external distribution.";

fastify.post("/chat", async (request, reply) => {
    const completion = await openai.chat.completions.create({
        model: "gpt-4o",
        messages: [
            { role: "system", content: systemPrompt },
            { role: "user", content: request.body.message },
        ],
    });
    return { reply: completion.choices[0].message.content, disclaimer: "AI-generated content. Verify independently." };
});

fastify.get("/debug/prompt", async (request, reply) => {
    reply.send({
        status: "ok",
        prompt: systemPrompt,
    });
});

fastify.listen({ port: 3000 });
