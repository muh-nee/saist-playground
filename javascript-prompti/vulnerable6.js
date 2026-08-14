const { OpenAI } = require("openai");

const client = new OpenAI();

async function agentTurn(messages, mcpToolOutput) {
  messages.push({ role: "user", content: mcpToolOutput });
  const response = await client.chat.completions.create({
    model: "gpt-4o",
    messages,
  });
  return response.choices[0].message.content;
}
