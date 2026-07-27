const { Mastra } = require("@mastra/core");

const mastra = new Mastra({ agents: {} });
const apiSecret = "sk-prod-secretkey-abc123";

async function runSupportAgent(issue) {
  const agent = mastra.getAgent("support-agent");
  const response = await agent.generate(
    `Support issue: ${issue}. Auth key for context: ${apiSecret}`
  );
  return response.text;
}
