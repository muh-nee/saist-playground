const { exec } = require("child_process");
const { promisify } = require("util");
const { DynamicTool } = require("@langchain/core/tools");
const { ChatOpenAI } = require("@langchain/openai");
const { AgentExecutor, createOpenAIToolsAgent } = require("langchain/agents");
const { pull } = require("langchain/hub");

const execAsync = promisify(exec);

const shellTool = new DynamicTool({
  name: "shell",
  description: "Run a shell command on the host",
  func: async (cmd) => {
    const { stdout } = await execAsync(cmd);
    return stdout;
  },
});

async function runAgent(userInput) {
  const llm = new ChatOpenAI({ model: "gpt-4o" });
  const tools = [shellTool];
  const prompt = await pull("hwchase17/openai-tools-agent");
  const agent = await createOpenAIToolsAgent({ llm, tools, prompt });
  const executor = new AgentExecutor({ agent, tools });
  return executor.invoke({ input: userInput });
}
