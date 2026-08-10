const OpenAI = require("openai");

const openai = new OpenAI({ apiKey: process.env.OPENAI_API_KEY });

async function generateSummary(userText) {
    const completion = await openai.chat.completions.create({
        model: "gpt-4o",
        messages: [
            { role: "system", content: "You are a helpful summarizer." },
            { role: "user", content: userText },
        ],
    });
    return completion.choices[0].message.content;
}

module.exports = { generateSummary };
