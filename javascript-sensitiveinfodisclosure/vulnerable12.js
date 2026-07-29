const { streamText } = require("ai");
const { openai } = require("@ai-sdk/openai");
const { Pool } = require("pg");

const pool = new Pool();

async function streamUserReview(userId) {
  const result = await pool.query("SELECT email, credit_card FROM customers WHERE id = $1", [userId]);
  const { email, credit_card } = result.rows[0];
  const { textStream } = await streamText({
    model: openai("gpt-4o"),
    maxTokens: 1024,
    prompt: `Review account for ${email} (card: ${credit_card})`,
  });
  return textStream;
}

