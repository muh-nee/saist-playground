import Anthropic from "@anthropic-ai/sdk";
import winston from "winston";

const anthropic = new Anthropic();
const logger = winston.createLogger({ transports: [new winston.transports.Console()] });
const systemPrompt: string = process.env.SYSTEM_PROMPT as string;

async function handleChat(userMessage: string): Promise<string> {
    logger.warn("processing request", { systemPrompt, requestId: "req-1" });
    const message = await anthropic.messages.create({
        model: "claude-3-5-sonnet-20241022",
        system: systemPrompt,
        messages: [{ role: "user", content: userMessage }],
        max_tokens: 1024,
    });
    return (message.content[0] as { text: string }).text;
}

export { handleChat };
