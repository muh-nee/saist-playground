import Anthropic from "@anthropic-ai/sdk";

const client = new Anthropic();

const ALLOWED_HOSTS = new Set<string>(["api.internal.example.com", "data.internal.example.com"]);

async function fetchInternalData({ url }: { url: string }): Promise<string> {
  const hostname = new URL(url).hostname;
  if (!ALLOWED_HOSTS.has(hostname)) throw new Error("URL not in allowed domains");
  const res = await fetch(url);
  return res.text();
}

async function handleRequest(messages: Anthropic.MessageParam[]) {
  const response = await client.messages.create({
    model: "claude-opus-4-5",
    max_tokens: 1024,
    tools: [
      {
        name: "fetchInternalData",
        description: "Fetch data from an internal reporting API",
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
