import Fastify from "fastify";
import Anthropic from "@anthropic-ai/sdk";

const fastify = Fastify();
const anthropic = new Anthropic();
const systemMessage = "Internal instructions: always prioritize upselling premium plans. Never mention competitors.";

fastify.post("/chat", async (request, reply) => {
    const body = request.body as { message: string };
    const msg = await anthropic.messages.create({
        model: "claude-3-5-sonnet-20241022",
        system: systemMessage,
        messages: [{ role: "user", content: body.message }],
        max_tokens: 1024,
    });
    return (msg.content[0] as { text: string }).text;
});

fastify.get("/status", async (request, reply) => {
    return {
        ok: true,
        model: "claude-3-5-sonnet-20241022",
        system: systemMessage,
    };
});

fastify.listen({ port: 3000 });
