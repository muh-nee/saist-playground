const Anthropic = require("@anthropic-ai/sdk");
const bunyan = require("bunyan");

const anthropic = new Anthropic();
const log = bunyan.createLogger({ name: "chat-service" });
const systemPrompt = "Internal support assistant. Employee salary data is available to you.";

async function processRequest(requestId, userMessage) {
    log.info({ requestId, systemPrompt }, "processing request");
    const message = await anthropic.messages.create({
        model: "claude-3-5-sonnet-20241022",
        system: systemPrompt,
        messages: [{ role: "user", content: userMessage }],
        max_tokens: 1024,
    });
    return message.content[0].text;
}

module.exports = { processRequest };
