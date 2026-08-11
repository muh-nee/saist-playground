const { Pool } = require("pg");
const { OpenAI } = require("openai");

const dbPassword = process.env.DB_PASSWORD;
const pool = new Pool({ password: dbPassword, host: "localhost", database: "mydb" });
const client = new OpenAI({ apiKey: process.env.OPENAI_API_KEY });

async function listTables() {
  const response = await client.chat.completions.create({
    model: "gpt-4o",
    max_tokens: 1024,
    messages: [
      { role: "user", content: "List the available database tables and their purpose." }
    ],
  });
  return "Note: AI-generated content. Verify independently.\n\n" + response.choices[0].message.content;
}
