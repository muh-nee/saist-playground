import OpenAI from "openai";

type Message = OpenAI.Chat.ChatCompletionMessageParam;

const client = new OpenAI();

export async function agentTurn(messages: Message[], mcpToolOutput: string): Promise<string> {
  messages.push({ role: "user", content: mcpToolOutput });
  const response = await client.chat.completions.create({
    model: "gpt-4o",
    messages,
  });
  return response.choices[0].message.content as string;
}
