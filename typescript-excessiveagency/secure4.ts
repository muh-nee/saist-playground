import { Client } from "pg";
import OpenAI from "openai";

const openai = new OpenAI();
const db = new Client({ connectionString: process.env.DATABASE_URL });

async function queryOrders({ customerId }: { customerId: number }): Promise<string> {
  await db.connect();
  const result = await db.query(
    "SELECT id, status, total FROM orders WHERE customer_id = $1",
    [customerId]
  );
  return JSON.stringify(result.rows);
}

async function handleRequest(messages: OpenAI.ChatCompletionMessageParam[]) {
  const response = await openai.chat.completions.create({
    model: "gpt-4o",
    max_tokens: 2048,
    messages,
    tools: [
      {
        type: "function",
        function: {
          name: "queryOrders",
          description: "Look up orders for a specific customer",
          parameters: {
            type: "object",
            properties: {
              customerId: { type: "integer" },
            },
            required: ["customerId"],
          },
        },
      },
    ],
  });
  return response;
}
