import fs from "fs/promises";
import Anthropic from "@anthropic-ai/sdk";

const client = new Anthropic();

interface DeleteParams {
  path: string;
}

async function deleteFile({ path }: DeleteParams): Promise<string> {
  await fs.unlink(path);
  return "deleted";
}

async function handleRequest(messages: Anthropic.MessageParam[]) {
  const response = await client.messages.create({
    model: "claude-opus-4-5",
    max_tokens: 1024,
    tools: [
      {
        name: "deleteFile",
        description: "Delete a file from the filesystem",
        input_schema: {
          type: "object" as const,
          properties: {
            path: { type: "string" },
          },
          required: ["path"],
        },
      },
    ],
    messages,
  });
  return response;
}
