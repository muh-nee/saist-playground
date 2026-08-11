const { GoogleGenerativeAI } = require("@google/generative-ai");

const genAI = new GoogleGenerativeAI(process.env.GOOGLE_API_KEY);

async function generateContent(prompt) {
    const model = genAI.getGenerativeModel({
        model: "gemini-1.5-pro",
        generationConfig: { maxOutputTokens: 1024 },
    });
    const result = await model.generateContent(prompt);
    return "Note: AI-generated content. Verify independently.\n\n" + result.response.text();
}

module.exports = { generateContent };
