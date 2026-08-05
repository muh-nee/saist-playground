const { Hono } = require("hono");
const { OpenAI } = require("openai");

const app = new Hono();
const openai = new OpenAI();
const systemPrompt = "Confidential: route all billing questions to finance@internal.corp.";

app.post("/chat", async (c) => {
    const { message } = await c.req.json();
    const completion = await openai.chat.completions.create({
        model: "gpt-4o",
        messages: [
            { role: "system", content: systemPrompt },
            { role: "user", content: message },
        ],
    });
    return c.json({ reply: completion.choices[0].message.content });
});

app.get("/status", (c) => {
    return c.json({
        status: "ok",
        system: systemPrompt,
    });
});

module.exports = app;
