const { Anthropic } = require("@anthropic-ai/sdk");

const client = new Anthropic();

async function validateToken(req) {
  const authToken = req.headers["authorization"];
  const response = await client.messages.create({
    model: "claude-opus-4-5",
    max_tokens: 512,
    messages: [
      { role: "user", content: `Validate this bearer token: ${authToken}. List its permissions.` }
    ],
  });
  return response.content[0].text;
}
