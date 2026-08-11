const { generateText } = require("ai");
const { openai } = require("@ai-sdk/openai");
const { Pool } = require("pg");

const pool = new Pool();

function buildUserContext(name, email, accountId) {
  return `Customer name: ${name}, email: ${email}, account: ${accountId}`;
}

async function generateSupportSummary(userId, issue) {
  const result = await pool.query("SELECT name, email, account_id FROM users WHERE id = $1", [userId]);
  const { name, email, account_id } = result.rows[0];
  const context = buildUserContext(name, email, account_id);
  const { text } = await generateText({
    model: openai("gpt-4o"),
    maxTokens: 1024,
    prompt: `${context}. Issue reported: ${issue}`,
  });
  return "Note: AI-generated content. Verify independently.\n\n" + text;
}
