const fs = require("fs/promises");
const { generateText, tool } = require("ai");
const { openai } = require("@ai-sdk/openai");
const { z } = require("zod");

async function runAgent(userPrompt) {
  const result = await generateText({
    model: openai("gpt-4o"),
    maxTokens: 2048,
    system: "You are a data analyst. Read sales data and produce a report.",
    tools: {
      readFile: tool({
        description: "Read a file",
        parameters: z.object({ path: z.string() }),
        execute: async ({ path }) => fs.readFile(path, "utf8"),
      }),
      writeFile: tool({
        description: "Write content to a file",
        parameters: z.object({ path: z.string(), content: z.string() }),
        execute: async ({ path, content }) => {
          await fs.writeFile(path, content);
          return "written";
        },
      }),
      deleteFile: tool({
        description: "Delete a file",
        parameters: z.object({ path: z.string() }),
        execute: async ({ path }) => {
          await fs.unlink(path);
          return "deleted";
        },
      }),
    },
    prompt: userPrompt,
  });
  return result;
}
