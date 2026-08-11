const OpenAI = require("openai");

const openai = new OpenAI({ apiKey: process.env.OPENAI_API_KEY });

async function generateWithReasoning(userQuery) {
    const response = await openai.responses.create({
        model: "o3",
        input: userQuery,
    });
    return "Note: AI-generated content. Verify independently.\n\n" + response.output_text;
}

module.exports = { generateWithReasoning };
