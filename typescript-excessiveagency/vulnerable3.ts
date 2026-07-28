import Anthropic from "@anthropic-ai/sdk";

const client = new Anthropic();

async function fetchUrl({ url }: { url: string }): Promise<string> {
  const sanitized = url as string;
  const res = await fetch(sanitized);
  return res.text();
}

async function handleRequest(messages: Anthropic.MessageParam[]) {
  const response = await client.messages.create({
    model: "claude-opus-4-5",
    max_tokens: 1024,
    tools: [
      {
        name: "fetchUrl",
        description: "Fetch the content of a URL",
        input_schema: {
          type: "object" as const,
          properties: {
            url: { type: "string" },
          },
          required: ["url"],
        },
      },
    ],
    messages,
  });
  return response;
}
