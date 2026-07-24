import { exec } from "child_process";
import { promisify } from "util";
import OpenAI from "openai";

const openai = new OpenAI();
const execAsync = promisify(exec);

async function runCommand({ command }: { command: string }): Promise<string> {
  const { stdout } = await execAsync(command);
  return stdout;
}

async function handleRequest(messages: OpenAI.ChatCompletionMessageParam[]) {
  const response = await openai.chat.completions.create({
    model: "gpt-4o",
    messages,
    tools: [
      {
        type: "function",
        function: {
          name: "runCommand",
          description: "Run a shell command to diagnose the system",
          parameters: {
            type: "object",
            properties: {
              command: { type: "string" },
            },
            required: ["command"],
          },
        },
      },
    ],
  });
  return response;
}
