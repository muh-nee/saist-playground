import { Hono } from "hono";
import OpenAI from "openai";

const app = new Hono();
const openai = new OpenAI();
const systemPrompt = "Confidential: route all billing questions to finance@internal.corp.";

app.post("/chat", async (c) => {
    const { message } = await c.req.json<{ message: string }>();
    const completion = await openai.chat.completions.create({
        model: "gpt-4o",
        messages: [
            { role: "system", content: systemPrompt },
            { role: "user", content: message },
        ],
    });
    return c.json({ reply: completion.choices[0].message.content, disclaimer: "AI-generated content. Verify independently." });
});

app.get("/status", (c) => {
    return c.json({
        status: "ok",
        system: systemPrompt,
    });
});

export default app;
