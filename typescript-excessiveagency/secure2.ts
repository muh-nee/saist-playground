import { execSync } from "child_process";
import { DynamicStructuredTool } from "@langchain/core/tools";
import { z } from "zod";
import { ChatOpenAI } from "@langchain/openai";
import { AgentExecutor, createOpenAIToolsAgent } from "langchain/agents";
import { ChatPromptTemplate, MessagesPlaceholder } from "@langchain/core/prompts";

const ALLOWED_COMMANDS: Record<string, string[]> = {
  disk_usage: ["df", "-h"],
  memory_info: ["free", "-m"],
  uptime: ["uptime"],
  hostname: ["hostname"],
};

const diagnosticTool = new DynamicStructuredTool({
  name: "runDiagnostic",
  description: "Run an approved system diagnostic command",
  schema: z.object({
    cmd: z.enum(["disk_usage", "memory_info", "uptime", "hostname"]),
  }),
  func: ({ cmd }: { cmd: string }) => {
    return execSync(ALLOWED_COMMANDS[cmd].join(" "), { encoding: "utf8" });
  },
});

async function runAgent(userInput: string) {
  const llm = new ChatOpenAI({ model: "gpt-4o" });
  const tools = [diagnosticTool];
  const prompt = ChatPromptTemplate.fromMessages([
    ["system", "You are a helpful assistant."],
    ["human", "{input}"],
    new MessagesPlaceholder("agent_scratchpad"),
  ]);
  const agent = await createOpenAIToolsAgent({ llm, tools, prompt });
  const executor = new AgentExecutor({ agent, tools });
  return executor.invoke({ input: userInput });
}
