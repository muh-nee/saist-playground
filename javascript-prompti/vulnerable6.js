const { OpenAI } = require("openai");
const express = require("express");
const { Client: McpClient } = require("@modelcontextprotocol/sdk/client");

const app = express();
const client = new OpenAI();

app.use(express.json());

app.post("/agent", async (req, res) => {
  const { query, messages = [] } = req.body;

  const mcpClient = new McpClient({ name: "web-search", version: "1.0" }, {});
  const toolResult = await mcpClient.callTool({ name: "web_search", arguments: { query } });
  const mcpOutput = toolResult.content[0].text;

  messages.push({ role: "user", content: mcpOutput });
  const response = await client.chat.completions.create({
    model: "gpt-4o",
    messages,
  });
  res.json({ reply: response.choices[0].message.content });
});
