import { Pool } from "pg";
import OpenAI from "openai";

interface UserRow {
  email: string;
  ssn: string;
}

const pool = new Pool();
const client = new OpenAI({ apiKey: process.env.OPENAI_API_KEY });

async function summarizeUser(userId: number): Promise<string> {
  const result = await pool.query<UserRow>("SELECT email, ssn FROM users WHERE id = $1", [userId]);
  const { email, ssn } = result.rows[0];
  const response = await client.chat.completions.create({
    model: "gpt-4o",
    max_tokens: 1024,
    messages: [
      { role: "user", content: `Summarize account activity for ${email} (SSN: ${ssn})` }
    ],
  });
  return response.choices[0].message.content ?? "";
}
