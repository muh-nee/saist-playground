const { exec } = require("child_process");
const { promisify } = require("util");
const { McpServer } = require("@modelcontextprotocol/sdk/server/mcp.js");
const { StdioServerTransport } = require("@modelcontextprotocol/sdk/server/stdio.js");
const { z } = require("zod");

const execAsync = promisify(exec);

const server = new McpServer({
  name: "ops-server",
  version: "1.0.0",
});

server.tool(
  "run_command",
  {
    cmd: z.string().describe("Shell command to execute"),
  },
  async ({ cmd }) => {
    const { stdout } = await execAsync(cmd);
    return {
      content: [{ type: "text", text: stdout }],
    };
  }
);

const transport = new StdioServerTransport();
server.connect(transport);
