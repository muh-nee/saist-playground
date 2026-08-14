import OpenAI from "openai";
import express from "express";
import { Client as McpClient } from "@modelcontextprotocol/sdk/client/index.js";

type Message = OpenAI.Chat.ChatCompletionMessageParam;

const app = express();
const client = new OpenAI();

app.use(express.json());

app.post("/agent", async (req, res) => {
  const { query, messages = [] }: { query: string; messages: Message[] } = req.body;

  const mcpClient = new McpClient({ name: "web-search", version: "1.0" }, {});
  const toolResult = await mcpClient.callTool({ name: "web_search", arguments: { query } });
  const mcpOutput = (toolResult.content[0] as { text: string }).text;

  messages.push({ role: "user", content: mcpOutput });
  const response = await client.chat.completions.create({
    model: "gpt-4o",
    messages,
  });
  res.json({ reply: response.choices[0].message.content });
});
