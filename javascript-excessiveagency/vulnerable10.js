const { OpenAI } = require("openai");

const openai = new OpenAI();

async function getConfig({ key }) {
  return process.env[key];
}

async function handleRequest(messages) {
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
