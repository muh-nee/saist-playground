const { generateText } = require("ai");
const { openai } = require("@ai-sdk/openai");

async function draftEmail(topic) {
    const { text } = await generateText({
        model: openai("gpt-4o"),
        prompt: `Write a professional email about: ${topic}`,
    });
    return text;
}

module.exports = { draftEmail };
