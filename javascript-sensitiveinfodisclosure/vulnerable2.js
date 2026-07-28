const { Anthropic } = require("@anthropic-ai/sdk");

const client = new Anthropic();

async function debugStripeError() {
  const stripeKey = process.env["STRIPE_SECRET_KEY"];
  const response = await client.messages.create({
    model: "claude-opus-4-5",
    max_tokens: 1024,
    messages: [
      { role: "user", content: `Stripe payment failing. Key in use: ${stripeKey}. Diagnose this.` }
    ],
  });
  return response.content[0].text;
}
