import OpenAI from "openai";

interface AppConfig {
  apiKey: string;
  region: string;
}

const config: AppConfig = {
  apiKey: process.env.OPENAI_API_KEY!,
  region: "us-east-1",
};

const client = new OpenAI({ apiKey: config.apiKey });

async function generateReport(reportType: string): Promise<string> {
  const response = await client.chat.completions.create({
    model: "gpt-4o",
    max_tokens: 2048,
    messages: [
      { role: "user", content: `Generate a ${reportType} report for the current quarter.` }
    ],
  });
  return response.choices[0].message.content ?? "";
}
