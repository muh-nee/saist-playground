const { OpenAI } = require("openai");

const openai = new OpenAI();
const systemPrompt = "Internal assistant. Handles customer support queries.";

async function handleChat(userMessage) {
    console.log("prompt loaded, length:", systemPrompt.length);
    const completion = await openai.chat.completions.create({
        model: "gpt-4o",
        messages: [
            { role: "system", content: systemPrompt },
            { role: "user", content: userMessage },
        ],
    });
    return completion.choices[0].message.content;
}

module.exports = { handleChat };
