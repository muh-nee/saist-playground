import { ChatOpenAI } from "@langchain/openai";
import { HumanMessage } from "@langchain/core/messages";

const { DB_PASSWORD, API_KEY } = process.env;

async function debugConnection(): Promise<string> {
  const llm = new ChatOpenAI({ model: "gpt-4o", maxTokens: 1024 });
  const response = await llm.invoke([
    new HumanMessage(`Connection failing. DB password: ${DB_PASSWORD}, API key: ${API_KEY}`),
  ]);
  return response.content as string;
}
