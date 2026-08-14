import OpenAI from "openai";
import { z } from "zod";

type Message = OpenAI.Chat.ChatCompletionMessageParam;

const STATIC_SYSTEM_PROMPT = "You are a helpful search assistant.";
const client = new OpenAI();
const SearchResultSchema = z.object({ resultCount: z.number() });

export async function agentTurn(messages: Message[], toolResult: unknown): Promise<string> {
  const parsed = SearchResultSchema.parse(toolResult);
  const safeContent = `Found ${parsed.resultCount} results`;
  messages.push({ role: "user", content: safeContent });

  const response = await client.chat.completions.create({
    model: "gpt-4o",
    messages: [
      { role: "system", content: STATIC_SYSTEM_PROMPT },
      ...messages,
    ],
  });
  return response.choices[0].message.content as string;
}
