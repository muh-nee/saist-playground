const crypto = require("crypto");
const { Pool } = require("pg");
const { OpenAI } = require("openai");

const pool = new Pool();
const client = new OpenAI({ apiKey: process.env.OPENAI_API_KEY });

async function analyzeUserActivity(userId) {
  const result = await pool.query("SELECT email FROM users WHERE id = $1", [userId]);
  const { email } = result.rows[0];
  const emailHash = crypto.createHash("sha256").update(email).digest("hex").slice(0, 8);
  const response = await client.chat.completions.create({
    model: "gpt-4o",
    max_tokens: 1024,
    messages: [
      { role: "user", content: `Analyze activity for user hash ${emailHash}` }
    ],
  });
  return response.choices[0].message.content;
}
