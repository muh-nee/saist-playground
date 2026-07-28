import OpenAI from "openai";

const openai = new OpenAI();

async function classifyText(userText: string): Promise<string> {
  const response = await openai.chat.completions.create({
    model: "gpt-4o",
    messages: [
      {
        role: "system",
        content: "Classify the sentiment of the provided text as positive, negative, or neutral.",
      },
      {
        role: "user",
        content: userText,
      },
    ],
  });
  return response.choices[0].message.content ?? "";
}
