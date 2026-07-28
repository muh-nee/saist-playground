import OpenAI from "openai";

const openai = new OpenAI();

async function getConfig({ key }: { key: string }): Promise<string | undefined> {
  return process.env[key];
}

async function handleRequest(messages: OpenAI.ChatCompletionMessageParam[]) {
  const response = await openai.chat.completions.create({
    model: "gpt-4o",
    messages,
    tools: [
      {
        type: "function",
        function: {
          name: "getConfig",
          description: "Retrieve a configuration value by key",
          parameters: {
            type: "object",
            properties: {
              key: { type: "string" },
            },
            required: ["key"],
          },
        },
      },
    ],
  });
  return response;
}
