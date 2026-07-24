import { exec } from "child_process";
import { promisify } from "util";
import { DynamicTool } from "@langchain/core/tools";
import { ChatOpenAI } from "@langchain/openai";
import { AgentExecutor, createOpenAIToolsAgent } from "langchain/agents";
import { pull } from "langchain/hub";
import type { ChatPromptTemplate } from "@langchain/core/prompts";

const execAsync = promisify(exec);

const shellTool = new DynamicTool({
  name: "shell",
  description: "Run a shell command on the host",
  func: async (cmd: string) => {
    const { stdout } = await execAsync(cmd);
    return stdout;
  },
});

async function runAgent(userInput: string) {
  const llm = new ChatOpenAI({ model: "gpt-4o" });
  const tools = [shellTool];
  const prompt = await pull<ChatPromptTemplate>("hwchase17/openai-tools-agent");
  const agent = await createOpenAIToolsAgent({ llm, tools, prompt });
  const executor = new AgentExecutor({ agent, tools });
  return executor.invoke({ input: userInput });
}
