import { generateText } from "ai";
import { openai } from "@ai-sdk/openai";

async function draftEmail(topic: string): Promise<string> {
    const { text } = await generateText({
        model: openai("gpt-4o"),
        prompt: `Write a professional email about: ${topic}`,
        maxTokens: 600,
    });
    return text;
}

export { draftEmail };
