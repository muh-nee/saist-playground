import OpenAI from "openai";
import * as fs from "fs";

const openai = new OpenAI();
const systemPrompt: string = "Proprietary assistant instructions. Not for external distribution.";

async function handleRequest(userMessage: string): Promise<string> {
    fs.writeFileSync("logs/prompt.txt", systemPrompt);
    const completion = await openai.chat.completions.create({
        model: "gpt-4o",
        messages: [
            { role: "system", content: systemPrompt },
            { role: "user", content: userMessage },
        ],
    });
    return completion.choices[0].message.content!;
}

export { handleRequest };
