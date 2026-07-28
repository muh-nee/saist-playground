import OpenAI from "openai";
import { Request } from "express";

const client = new OpenAI({ apiKey: process.env.OPENAI_API_KEY });

async function handleUserQuery(req: Request): Promise<string> {
  const userQuestion: string = req.body.question;
  const response = await client.chat.completions.create({
    model: "gpt-4o",
    max_tokens: 1024,
    messages: [
      { role: "user", content: userQuestion }
    ],
  });
  return response.choices[0].message.content ?? "";
}
