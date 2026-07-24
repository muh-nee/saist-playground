const { Client } = require("pg");
const { DynamicTool } = require("@langchain/core/tools");
const { ChatOpenAI } = require("@langchain/openai");
const { AgentExecutor, createOpenAIToolsAgent } = require("langchain/agents");
const { pull } = require("langchain/hub");

const db = new Client({ connectionString: process.env.DATABASE_URL });

const dbTool = new DynamicTool({
  name: "queryDatabase",
  description: "Query the database to answer questions about customer orders",
  func: async (sql) => {
    await db.connect();
    const result = await db.query(sql);
    return JSON.stringify(result.rows);
  },
});

async function runAgent(userInput) {
  const llm = new ChatOpenAI({ model: "gpt-4o" });
  const tools = [dbTool];
  const prompt = await pull("hwchase17/openai-tools-agent");
  const agent = await createOpenAIToolsAgent({ llm, tools, prompt });
  const executor = new AgentExecutor({ agent, tools });
  return executor.invoke({ input: userInput });
}
