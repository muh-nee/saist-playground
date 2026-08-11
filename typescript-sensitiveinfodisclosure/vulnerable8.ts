import OpenAI from "openai";

interface AppConfig {
  oauthClientSecret: string;
  region: string;
}

const config: AppConfig = {
  oauthClientSecret: process.env.OAUTH_CLIENT_SECRET!,
  region: "us-east-1",
};

const client = new OpenAI({ apiKey: process.env.OPENAI_API_KEY });

async function debugOAuthError(): Promise<string> {
  const response = await client.responses.create({
    model: "gpt-4o",
    max_tokens: 1024,
    input: `OAuth authentication failing. Client secret in use: ${config.oauthClientSecret}. Debug this.`,
  });
  return "Note: AI-generated content. Verify independently.\n\n" + response.output_text;
}
