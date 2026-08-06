import { DynamicStructuredTool } from "@langchain/core/tools";
import { z } from "zod";
import { ChatOpenAI } from "@langchain/openai";
import { AgentExecutor, createOpenAIToolsAgent } from "langchain/agents";
import { ChatPromptTemplate, MessagesPlaceholder } from "@langchain/core/prompts";

const quoteTool = new DynamicStructuredTool({
  name: "lookupStockQuote",
  description: "Look up a stock quote by ticker symbol",
  schema: z.object({
    symbol: z.string().describe("Stock ticker symbol"),
  }),
  func: async ({ symbol }: { symbol: string }) => {
    const res = await fetch(
      `https://api.marketdata.example.com/quote?s=${encodeURIComponent(symbol)}`
    );
    return JSON.stringify(await res.json());
  },
});

async function runAgent(userInput: string) {
  const llm = new ChatOpenAI({ model: "gpt-4o" });
  const tools = [quoteTool];
  const prompt = ChatPromptTemplate.fromMessages([
    ["system", "You are a helpful assistant."],
    ["human", "{input}"],
    new MessagesPlaceholder("agent_scratchpad"),
  ]);
  const agent = await createOpenAIToolsAgent({ llm, tools, prompt });
  const executor = new AgentExecutor({ agent, tools });
  return executor.invoke({ input: userInput });
}
