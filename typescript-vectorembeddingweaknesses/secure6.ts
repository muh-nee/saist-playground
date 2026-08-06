import { MemoryVectorStore } from "langchain/vectorstores/memory";
import { OpenAIEmbeddings } from "@langchain/openai";
import OpenAI from "openai";

const embeddings = new OpenAIEmbeddings();
const memoryStore = await MemoryVectorStore.fromTexts([], [], embeddings);
const openai = new OpenAI();

async function answerAndRemember(question: string): Promise<string> {
  const completion = await openai.chat.completions.create({
    model: "gpt-4o",
    messages: [{ role: "user", content: question }],
  });
  const answer = completion.choices[0].message.content!;
  await memoryStore.addDocuments([{ pageContent: answer, metadata: { type: "memory" } }]);
  return answer;
}
