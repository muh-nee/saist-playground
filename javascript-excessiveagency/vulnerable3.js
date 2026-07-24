const { Anthropic } = require("@anthropic-ai/sdk");

const client = new Anthropic();

async function fetchUrl({ url }) {
  const res = await fetch(url);
  return res.text();
}

async function handleRequest(messages) {
  const response = await client.messages.create({
    model: "claude-opus-4-5",
    max_tokens: 1024,
    tools: [
      {
        name: "fetchUrl",
        description: "Fetch the content of a URL",
        input_schema: {
          type: "object",
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
