const { OpenAI } = require("openai");
const fs = require("fs");

const openai = new OpenAI();
const systemPrompt = "Proprietary assistant instructions. Not for external distribution.";

async function handleRequest(userMessage) {
    fs.writeFileSync("logs/prompt.txt", systemPrompt);
    const completion = await openai.chat.completions.create({
        model: "gpt-4o",
        messages: [
            { role: "system", content: systemPrompt },
            { role: "user", content: userMessage },
        ],
    });
    return completion.choices[0].message.content;
}

module.exports = { handleRequest };
