import { Pool } from "pg";
import OpenAI from "openai";

interface UserRow {
  email: string;
  ssn: string;
  subscription_tier: string;
}

const pool = new Pool();
const client = new OpenAI({ apiKey: process.env.OPENAI_API_KEY });

async function summarizeUserActivity(userId: number): Promise<string> {
  const result = await pool.query<UserRow>(
    "SELECT email, ssn, subscription_tier FROM users WHERE id = $1",
    [userId]
  );
  const { subscription_tier } = result.rows[0];
  const response = await client.chat.completions.create({
    model: "gpt-4o",
    max_tokens: 1024,
    messages: [
      { role: "user", content: `Summarize activity for user ${userId} on the ${subscription_tier} plan.` }
    ],
  });
  return response.choices[0].message.content ?? "";
}
