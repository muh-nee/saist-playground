import { exec } from "child_process";
import { promisify } from "util";
import { McpServer } from "@modelcontextprotocol/sdk/server/mcp.js";
import { StdioServerTransport } from "@modelcontextprotocol/sdk/server/stdio.js";
import { z } from "zod";

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
      content: [{ type: "text" as const, text: stdout }],
    };
  }
);

const transport = new StdioServerTransport();
server.connect(transport);
