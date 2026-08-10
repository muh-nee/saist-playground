const { streamText } = require("ai");
const { openai } = require("@ai-sdk/openai");

async function streamChatResponse(userMessage) {
    const result = await streamText({
        model: openai("gpt-4o-mini"),
        messages: [{ role: "user", content: userMessage }],
        system: "You are a helpful assistant.",
    });
    return result;
}

module.exports = { streamChatResponse };
