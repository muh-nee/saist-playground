const { generateObject } = require("ai");
const { openai } = require("@ai-sdk/openai");
const { z } = require("zod");

async function extractProductInfo(description) {
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

module.exports = { extractProductInfo };
