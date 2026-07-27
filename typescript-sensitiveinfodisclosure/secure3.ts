import { Pool } from "pg";
import OpenAI from "openai";

const dbPassword: string = process.env.DB_PASSWORD!;
const pool = new Pool({ password: dbPassword, host: "localhost", database: "mydb" });
const client = new OpenAI({ apiKey: process.env.OPENAI_API_KEY });

async function listTables(): Promise<string> {
  const response = await client.chat.completions.create({
    model: "gpt-4o",
    max_tokens: 1024,
    messages: [
      { role: "user", content: "List the available database tables and their purpose." }
    ],
  });
  return response.choices[0].message.content ?? "";
}
