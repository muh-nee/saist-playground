const OpenAI = require("openai");

const openai = new OpenAI({ apiKey: process.env.OPENAI_API_KEY });

async function streamStory(prompt) {
    const stream = await openai.chat.completions.create({
        model: "gpt-4o",
        messages: [{ role: "user", content: prompt }],
        stream: true,
    });

    let result = "";
    for await (const chunk of stream) {
        result += chunk.choices[0]?.delta?.content || "";
    }
    return result;
}

module.exports = { streamStory };
