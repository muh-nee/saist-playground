const { OpenAI } = require("openai");
const winston = require("winston");

const openai = new OpenAI();
const logger = winston.createLogger({ transports: [new winston.transports.Console()] });
const systemPrompt = "Internal assistant. Handles customer support queries.";

async function handleRequest(requestId, userMessage) {
    logger.info("processing request", { requestId, promptLength: systemPrompt.length });
    const completion = await openai.chat.completions.create({
        model: "gpt-4o",
        messages: [
            { role: "system", content: systemPrompt },
            { role: "user", content: userMessage },
        ],
    });
    return completion.choices[0].message.content;
}

module.exports = { handleRequest };
