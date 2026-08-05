const { OpenAI } = require("openai");
const pino = require("pino");

const openai = new OpenAI();
const logger = pino();
const systemPrompt = "Internal assistant. Employee salary data and customer records are available.";

async function processRequest(requestId, userMessage) {
    logger.info({ requestId, prompt: systemPrompt }, "sending LLM request");
    const completion = await openai.chat.completions.create({
        model: "gpt-4o",
        messages: [
            { role: "system", content: systemPrompt },
            { role: "user", content: userMessage },
        ],
    });
    return completion.choices[0].message.content;
}

module.exports = { processRequest };
