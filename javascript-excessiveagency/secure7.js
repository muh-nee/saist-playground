const { DynamicStructuredTool } = require("@langchain/core/tools");
const { z } = require("zod");
const { ChatOpenAI } = require("@langchain/openai");
const { AgentExecutor, createOpenAIToolsAgent } = require("langchain/agents");
const { pull } = require("langchain/hub");

const quoteTool = new DynamicStructuredTool({
  name: "lookupStockQuote",
  description: "Look up a stock quote by ticker symbol",
  schema: z.object({
    symbol: z.string().describe("Stock ticker symbol"),
  }),
  func: async ({ symbol }) => {
    const res = await fetch(
      `https://api.marketdata.example.com/quote?s=${encodeURIComponent(symbol)}`
    );
    return JSON.stringify(await res.json());
  },
});

async function runAgent(userInput) {
  const llm = new ChatOpenAI({ model: "gpt-4o" });
  const tools = [quoteTool];
  const prompt = await pull("hwchase17/openai-tools-agent");
  const agent = await createOpenAIToolsAgent({ llm, tools, prompt });
  const executor = new AgentExecutor({ agent, tools });
  return executor.invoke({ input: userInput });
}
