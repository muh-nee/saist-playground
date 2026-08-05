import OpenAI from "openai";

const openai = new OpenAI();
const systemPrompt: string = "Internal assistant. Handles customer support queries.";

async function handleChat(userMessage: string): Promise<string> {
    console.log("prompt loaded, length:", systemPrompt.length);
    const completion = await openai.chat.completions.create({
        model: "gpt-4o",
        messages: [
            { role: "system", content: systemPrompt },
            { role: "user", content: userMessage },
        ],
    });
    return completion.choices[0].message.content!;
}

export { handleChat };
