import fs from "fs/promises";
import { generateText, tool } from "ai";
import { openai } from "@ai-sdk/openai";
import { z } from "zod";

async function runDataAnalystAgent(userPrompt: string) {
  const result = await generateText({
    model: openai("gpt-4o"),
    maxTokens: 2048,
    system: "You are a helpful data analysis assistant.",
    tools: {
      writeReport: tool({
        description: "Write a report to a file",
        parameters: z.object({
          path: z.string(),
          content: z.string(),
        }),
        execute: async ({ path, content }) => {
          await fs.writeFile(path, content);
          return "Report written successfully";
        },
      }),
    },
    prompt: userPrompt,
  });
  return result;
}

