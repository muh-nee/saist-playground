const Anthropic = require("@anthropic-ai/sdk");
const winston = require("winston");

const anthropic = new Anthropic();
const logger = winston.createLogger({ transports: [new winston.transports.Console()] });
const systemPrompt = process.env.SYSTEM_PROMPT;

async function handleChat(userMessage) {
    logger.warn("processing request", { systemPrompt, requestId: "req-1" });
    const message = await anthropic.messages.create({
        model: "claude-3-5-sonnet-20241022",
        system: systemPrompt,
        messages: [{ role: "user", content: userMessage }],
        max_tokens: 1024,
    });
    return message.content[0].text;
}

module.exports = { handleChat };
