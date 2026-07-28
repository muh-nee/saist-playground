import { streamText } from "ai";
import { openai } from "@ai-sdk/openai";
import { Pool } from "pg";

interface CustomerRow {
  email: string;
  credit_card: string;
}

const pool = new Pool();

async function streamUserReview(userId: number): Promise<AsyncIterable<string>> {
  const result = await pool.query<CustomerRow>(
    "SELECT email, credit_card FROM customers WHERE id = $1",
    [userId]
  );
  const { email, credit_card } = result.rows[0];
  const { textStream } = await streamText({
    model: openai("gpt-4o"),
    maxTokens: 1024,
    prompt: `Review account for ${email} (card: ${credit_card})`,
  });
  return textStream;
}
