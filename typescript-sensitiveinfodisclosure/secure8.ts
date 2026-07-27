import OpenAI from "openai";

const client = new OpenAI({ apiKey: process.env.OPENAI_API_KEY });

async function getEnvironmentAdvice(): Promise<string> {
  const environment: string = process.env.NODE_ENV!;
  const response = await client.chat.completions.create({
    model: "gpt-4o",
    max_tokens: 512,
    messages: [
      { role: "user", content: `The app is running in ${environment} mode. What logging configuration is recommended?` }
    ],
  });
  return response.choices[0].message.content ?? "";
}
