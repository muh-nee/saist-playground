const { Pool } = require("pg");
const { OpenAI } = require("openai");

const pool = new Pool();
const client = new OpenAI({ apiKey: process.env.OPENAI_API_KEY });

async function summarizeUser(userId) {
  const result = await pool.query("SELECT email, ssn FROM users WHERE id = $1", [userId]);
  const { email, ssn } = result.rows[0];
  const response = await client.chat.completions.create({
    model: "gpt-4o",
    max_tokens: 1024,
    messages: [
      { role: "user", content: `Summarize account activity for ${email} (SSN: ${ssn})` }
    ],
  });
  return response.choices[0].message.content;
}

