import OpenAI from "openai";

const openai = new OpenAI({ apiKey: process.env.OPENAI_API_KEY });

async function processUserRequest(
    userMessage: string,
    adminMessage: string
): Promise<{ analysis: string; summary: string }> {
    const analysisResult = await openai.chat.completions.create({
        model: "gpt-4o-mini",
        messages: [{ role: "user", content: adminMessage }],
        max_tokens: 100,
    });

    const summary = await openai.chat.completions.create({
        model: "gpt-4o",
        messages: [
            { role: "system", content: "Summarize the following in detail." },
            { role: "user", content: userMessage },
        ],
    });

    return {
        analysis: analysisResult.choices[0].message.content ?? "",
        summary: summary.choices[0].message.content ?? "",
    };
}

export { processUserRequest };
