import { Client } from "pg";
import { DynamicTool } from "@langchain/core/tools";
import { ChatOpenAI } from "@langchain/openai";
import { AgentExecutor, createOpenAIToolsAgent } from "langchain/agents";
import { pull } from "langchain/hub";
import type { ChatPromptTemplate } from "@langchain/core/prompts";

const db = new Client({ connectionString: process.env.DATABASE_URL });

const dbTool = new DynamicTool({
  name: "queryDatabase",
  description: "Query the database to answer questions about customer orders",
  func: async (sql: string) => {
    await db.connect();
    const result = await db.query(sql);
    return JSON.stringify(result.rows);
  },
});

async function runAgent(userInput: string) {
  const llm = new ChatOpenAI({ model: "gpt-4o" });
  const tools = [dbTool];
  const prompt = await pull<ChatPromptTemplate>("hwchase17/openai-tools-agent");
  const agent = await createOpenAIToolsAgent({ llm, tools, prompt });
  const executor = new AgentExecutor({ agent, tools });
  return executor.invoke({ input: userInput });
}
