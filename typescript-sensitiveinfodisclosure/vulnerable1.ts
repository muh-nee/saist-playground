import OpenAI from "openai";

const client = new OpenAI({ apiKey: process.env.OPENAI_API_KEY });

async function debugDatabaseError(): Promise<string> {
  const dbPassword: string = process.env.DB_PASSWORD!;
  const response = await client.chat.completions.create({
    model: "gpt-4o",
    max_tokens: 1024,
    messages: [
      { role: "user", content: `Debug this DB connection error. Password in use: ${dbPassword}` }
    ],
  });
  return response.choices[0].message.content ?? "";
}


