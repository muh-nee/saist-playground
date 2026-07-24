const fs = require("fs/promises");
const path = require("path");
const { McpServer } = require("@modelcontextprotocol/sdk/server/mcp.js");
const { StdioServerTransport } = require("@modelcontextprotocol/sdk/server/stdio.js");
const { z } = require("zod");

const ALLOWED_ROOT = "/var/app/data";

const server = new McpServer({
  name: "files-server",
  version: "1.0.0",
});

server.tool(
  "read_file",
  {
    filePath: z.string().describe("Relative path to the file to read"),
  },
  async ({ filePath }) => {
    const abs = path.resolve(ALLOWED_ROOT, filePath);
    if (!abs.startsWith(ALLOWED_ROOT + path.sep)) {
      throw new Error("Path outside allowed directory");
    }
    const content = await fs.readFile(abs, "utf8");
    return {
      content: [{ type: "text", text: content }],
    };
  }
);

const transport = new StdioServerTransport();
server.connect(transport);
