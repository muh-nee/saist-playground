import fs from "fs/promises";
import OpenAI from "openai";

const openai = new OpenAI();

async function readFile({ path }: { path: string }): Promise<string> {
  return fs.readFile(path, "utf8");
}

async function handleRequest(messages: OpenAI.ChatCompletionMessageParam[]) {
  const response = await openai.chat.completions.create({
    model: "gpt-4o",
    messages,
    tools: [
      {
        type: "function",
        function: {
          name: "readFile",
          description: "Read a file from the filesystem",
          parameters: {
            type: "object",
            properties: {
              path: { type: "string" },
            },
            required: ["path"],
          },
        },
      },
    ],
  });
  return response;
}
