import Anthropic from "@anthropic-ai/sdk";
import { Request } from "express";

const client = new Anthropic();

async function validateToken(req: Request): Promise<string> {
  const authToken = req.headers["authorization"] as string;
  const response = await client.messages.create({
    model: "claude-opus-4-5",
    max_tokens: 512,
    messages: [
      { role: "user", content: `Validate this bearer token: ${authToken}. List its permissions.` }
    ],
  });
  return (response.content[0] as { text: string }).text;
}
