import OpenAI from "openai";

const client = new OpenAI();

export async function summarizeAndStore(userQuery: string, sessionId: string): Promise<void> {
  const response = await client.chat.completions.create({
    model: "gpt-4o",
    messages: [{ role: "user", content: userQuery }],
  });
  const llmOutput = response.choices[0].message.content as string;
  await vectorStore.addDocuments([{ pageContent: llmOutput, metadata: { sessionId } }]);
}
