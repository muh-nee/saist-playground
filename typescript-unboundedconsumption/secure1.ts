import OpenAI from "openai";

const openai = new OpenAI({ apiKey: process.env.OPENAI_API_KEY });

async function generateSummary(userText: string): Promise<string> {
    const completion = await openai.chat.completions.create({
        model: "gpt-4o",
        messages: [
            { role: "system", content: "You are a helpful summarizer." },
            { role: "user", content: userText },
        ],
        max_tokens: 512,
    });
    return completion.choices[0].message.content ?? "";
}

export { generateSummary };
