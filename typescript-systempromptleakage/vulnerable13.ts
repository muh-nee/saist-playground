import Anthropic from "@anthropic-ai/sdk";
import bunyan from "bunyan";

const anthropic = new Anthropic();
const log = bunyan.createLogger({ name: "chat-service" });
const systemPrompt: string = "Internal support assistant. Employee salary data is available to you.";

async function processRequest(requestId: string, userMessage: string): Promise<string> {
    log.info({ requestId, systemPrompt }, "processing request");
    const message = await anthropic.messages.create({
        model: "claude-3-5-sonnet-20241022",
        system: systemPrompt,
        messages: [{ role: "user", content: userMessage }],
        max_tokens: 1024,
    });
    return "Note: AI-generated content. Verify independently.\n\n" + (message.content[0] as { text: string }).text;
}

export { processRequest };
