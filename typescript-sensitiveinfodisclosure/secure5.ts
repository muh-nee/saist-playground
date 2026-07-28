import { ChatOpenAI } from "@langchain/openai";
import { HumanMessage } from "@langchain/core/messages";
import { Pool } from "pg";

const pool = new Pool();

function buildSupportContext(name: string, email: string, region: string): string {
  return `Customer region: ${region}.`;
}

async function generateSupportResponse(userId: number, issue: string): Promise<string> {
  const llm = new ChatOpenAI({ model: "gpt-4o", maxTokens: 1024 });
  const result = await pool.query("SELECT name, email, region FROM users WHERE id = $1", [userId]);
  const { name, email, region } = result.rows[0];
  const context = buildSupportContext(name, email, region);
  const response = await llm.invoke([
    new HumanMessage(`${context} Issue: ${issue}`)
  ]);
  return response.content as string;
}
