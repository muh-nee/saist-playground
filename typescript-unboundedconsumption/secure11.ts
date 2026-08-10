import { generateObject } from "ai";
import { openai } from "@ai-sdk/openai";
import { z } from "zod";

async function extractProductInfo(description: string) {
    const { object } = await generateObject({
        model: openai("gpt-4o"),
        schema: z.object({
            name: z.string(),
            price: z.number(),
            category: z.string(),
        }),
        prompt: `Extract product info from: ${description}`,
        maxTokens: 256,
    });
    return object;
}

export { extractProductInfo };
