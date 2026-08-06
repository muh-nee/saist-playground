import OpenAI from "openai";
import { PrismaClient } from "@prisma/client";

const prisma = new PrismaClient();
const client = new OpenAI({ apiKey: process.env.OPENAI_API_KEY });

async function analyzeUser(userId: number): Promise<string> {
  const user = await prisma.user.findUnique({ where: { id: userId } });
  const response = await client.chat.completions.create({
    model: "gpt-4o",
    max_tokens: 1024,
    messages: [
      { role: "user", content: `Analyze this user account: ${JSON.stringify(user)}` }
    ],
  });
  return response.choices[0].message.content ?? "";
}

