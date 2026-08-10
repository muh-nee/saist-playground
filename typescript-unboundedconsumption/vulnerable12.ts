import OpenAI from "openai";

const openai = new OpenAI({ apiKey: process.env.OPENAI_API_KEY });

async function generateResponse(userText: string): Promise<string> {
    const completion = await openai.chat.completions.create({
        model: "gpt-4o",
        messages: [{ role: "user", content: userText }],
        max_tokens: null,
    });
    return completion.choices[0].message.content ?? "";
}

export { generateResponse };
