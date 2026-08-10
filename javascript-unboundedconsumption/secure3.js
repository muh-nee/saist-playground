const OpenAI = require("openai");

const openai = new OpenAI({ apiKey: process.env.OPENAI_API_KEY });

async function generateWithReasoning(userQuery) {
    const response = await openai.responses.create({
        model: "o3",
        input: userQuery,
        max_output_tokens: 2048,
    });
    return response.output_text;
}

module.exports = { generateWithReasoning };
