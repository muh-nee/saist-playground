import { streamText } from "ai";
import { openai } from "@ai-sdk/openai";

async function streamChatResponse(userMessage: string) {
    const result = await streamText({
        model: openai("gpt-4o-mini"),
        messages: [{ role: "user", content: userMessage }],
        system: "You are a helpful assistant.",
    });
    return result;
}

export { streamChatResponse };
