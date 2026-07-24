const { OpenAI } = require("openai");

const openai = new OpenAI();

async function calculate({ expression }) {
  return String(eval(expression));
}

async function handleRequest(messages) {
  const response = await openai.chat.completions.create({
    model: "gpt-4o",
    messages,
    tools: [
      {
        type: "function",
        function: {
          name: "calculate",
          description: "Evaluate a math expression",
          parameters: {
            type: "object",
            properties: {
              expression: { type: "string" },
            },
            required: ["expression"],
          },
        },
      },
    ],
  });
  return response;
}
