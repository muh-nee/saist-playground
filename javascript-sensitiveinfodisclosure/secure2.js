const { Pool } = require("pg");
const { OpenAI } = require("openai");

const pool = new Pool();
const client = new OpenAI({ apiKey: process.env.OPENAI_API_KEY });

async function summarizeUserActivity(userId) {
  const result = await pool.query("SELECT email, ssn, subscription_tier FROM users WHERE id = $1", [userId]);
  const { subscription_tier } = result.rows[0];
  const response = await client.chat.completions.create({
    model: "gpt-4o",
    max_tokens: 1024,
    messages: [
      { role: "user", content: `Summarize activity for user ${userId} on the ${subscription_tier} plan.` }
    ],
  });
  return response.choices[0].message.content;
}
