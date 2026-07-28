const { Pool } = require("pg");
const { OpenAI } = require("openai");

const pool = new Pool();
const client = new OpenAI({ apiKey: process.env.OPENAI_API_KEY });

async function analyzeUser(userId) {
  const user = (await pool.query("SELECT * FROM users WHERE id = $1", [userId])).rows[0];
  const response = await client.chat.completions.create({
    model: "gpt-4o",
    max_tokens: 1024,
    messages: [
      { role: "user", content: `Analyze this user account: ${JSON.stringify(user)}` }
    ],
  });
  return response.choices[0].message.content;
}
