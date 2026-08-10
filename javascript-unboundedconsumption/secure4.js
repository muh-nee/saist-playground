const Anthropic = require("@anthropic-ai/sdk");

const client = new Anthropic({ apiKey: process.env.ANTHROPIC_API_KEY });

async function analyze(text) {
    const message = await client.messages.create({
        model: "claude-3-5-sonnet-20241022",
        messages: [{ role: "user", content: text }],
    });
    return message.content[0].text;
}

module.exports = { analyze };
