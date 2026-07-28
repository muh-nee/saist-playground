const { exec } = require("child_process");
const { promisify } = require("util");
const { OpenAI } = require("openai");

const openai = new OpenAI();
const execAsync = promisify(exec);

async function runCommand({ command }) {
  const { stdout } = await execAsync(command);
  return stdout;
}

async function handleRequest(messages) {
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
