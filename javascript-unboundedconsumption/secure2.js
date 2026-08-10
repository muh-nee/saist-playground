const OpenAI = require("openai");

const openai = new OpenAI({ apiKey: process.env.OPENAI_API_KEY });

const MAX_RESPONSE_TOKENS = 1024;

async function generateSummary(userText) {
    const completion = await openai.chat.completions.create({
        model: "gpt-4o",
        messages: [{ role: "user", content: userText }],
        max_tokens: MAX_RESPONSE_TOKENS,
    });
    return completion.choices[0].message.content;
}

module.exports = { generateSummary };
