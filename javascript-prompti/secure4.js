const { OpenAI } = require("openai");

const STATIC_SYSTEM_PROMPT = "You are a helpful search assistant.";
const client = new OpenAI();

async function agentTurn(messages, toolResult) {
  if (typeof toolResult?.resultCount !== "number") {
    throw new Error("Unexpected MCP tool output format");
  }
  const safeContent = `Found ${toolResult.resultCount} results`;
  messages.push({ role: "user", content: safeContent });

  const response = await client.chat.completions.create({
    model: "gpt-4o",
    messages: [
      { role: "system", content: STATIC_SYSTEM_PROMPT },
      ...messages,
    ],
  });
  return response.choices[0].message.content;
}
