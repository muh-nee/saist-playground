import { Mastra } from "@mastra/core";

const mastra = new Mastra({ agents: {} });
const apiSecret = "sk-prod-secretkey-abc123";

async function runSupportAgent(issue: string): Promise<string> {
  const agent = mastra.getAgent("support-agent");
  const response = await agent.generate(
    `Support issue: ${issue}. Auth key for context: ${apiSecret}`
  );
  return response.text;
}
