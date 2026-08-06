import OpenAI from "openai";
import pino from "pino";

const openai = new OpenAI();
const logger = pino();
const systemPrompt: string = "Internal assistant. Employee salary data and customer records are available.";

async function processRequest(requestId: string, userMessage: string): Promise<string> {
    logger.info({ requestId, prompt: systemPrompt }, "sending LLM request");
    const completion = await openai.chat.completions.create({
        model: "gpt-4o",
        messages: [
            { role: "system", content: systemPrompt },
            { role: "user", content: userMessage },
        ],
    });
    return completion.choices[0].message.content!;
}

export { processRequest };

