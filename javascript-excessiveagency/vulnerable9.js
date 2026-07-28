const { spawn } = require("child_process");
const { OpenAI } = require("openai");

const openai = new OpenAI();

async function runProcess({ program, args }) {
  return new Promise((resolve, reject) => {
    const proc = spawn(program, args);
    let output = "";
    proc.stdout.on("data", (d) => { output += d.toString(); });
    proc.on("close", (code) => { code === 0 ? resolve(output) : reject(new Error(`Exit ${code}`)); });
  });
}

async function handleRequest(messages) {
  const response = await openai.chat.completions.create({
    model: "gpt-4o",
    max_tokens: 2048,
    messages,
    tools: [
      {
        type: "function",
        function: {
          name: "runProcess",
          description: "Spawn a process with the given program and arguments",
          parameters: {
            type: "object",
            properties: {
              program: { type: "string" },
              args: { type: "array", items: { type: "string" } },
            },
            required: ["program", "args"],
          },
        },
      },
    ],
  });
  return response;
}
