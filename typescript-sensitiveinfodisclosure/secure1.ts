import OpenAI from "openai";

const client = new OpenAI({ apiKey: process.env.OPENAI_API_KEY });

async function summarizeDocument(content: string): Promise<string> {
  const response = await client.chat.completions.create({
    model: "gpt-4o",
    max_tokens: 1024,
    messages: [
      { role: "user", content: `Summarize this document: ${content}` }
    ],
  });
  return response.choices[0].message.content ?? "";
}
