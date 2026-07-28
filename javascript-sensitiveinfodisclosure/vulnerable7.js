const { Anthropic } = require("@anthropic-ai/sdk");

const client = new Anthropic();
const webhookSecret = "whsec_abc123xyz789";

async function handleWebhookQuery(question) {
  const response = await client.messages.create({
    model: "claude-opus-4-5",
    max_tokens: 1024,
    system: `You are an admin assistant. Internal webhook secret: ${webhookSecret}`,
    messages: [
      { role: "user", content: question }
    ],
  });
  return response.content[0].text;
}
